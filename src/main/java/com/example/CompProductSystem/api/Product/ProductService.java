package com.example.CompProductSystem.api.Product;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Category.CategoryRepository;
import com.example.CompProductSystem.api.PriceInfo.PriceInfo;
import com.example.CompProductSystem.api.PriceInfo.Repository.PriceInfoRepository;
import com.example.CompProductSystem.api.Product.Repository.ProductRepository;
import com.example.CompProductSystem.api.Product.Repository.ProductSearchRepo;
import com.example.CompProductSystem.api.Product.Search.dto.ProductSearchCondition;
import com.example.CompProductSystem.api.Product.dto.request.FurnitureRequest;
import com.example.CompProductSystem.api.Product.dto.request.LaptopRequest;
import com.example.CompProductSystem.api.Product.dto.request.TvRequest;
import com.example.CompProductSystem.api.Product.dto.response.ProductDetailResponse;
import com.example.CompProductSystem.api.Product.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductSearchRepo productSearchRepo;
    private final PriceInfoRepository priceInfoRepository;
    private final ConcurrentHashMap<Long, AtomicLong> viewCountCache = new ConcurrentHashMap<>();
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 특정 카테고리에 속한 상품들을 페이징하여 조회하는 메서드
     *
     * @param categoryId 조회할 카테고리의 ID
     * @param pageable   페이지 정보
     * @return 해당 카테고리의 상품들을 페이징하여 ProductResponse DTO로 변환한 결과
     */
    public Page<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable)
                .map(ProductResponse::from);
    }

    /**
     * 특정 카테고리와 그 하위의 모든 카테고리에 속한 상품들을 페이징하여 조회하는 메서드
     * 카테고리의 계층 구조를 path를 이용하여 조회
     *
     * @param categoryId 조회할 최상위 카테고리의 ID
     * @param pageable   페이지 정보
     * @return 해당 카테고리와 하위 카테고리의 모든 상품들을 페이징하여 ProductResponse DTO로 변환한 결과
     * @throws IllegalArgumentException 카테고리 ID가 존재하지 않는 경우
     */
    public Page<ProductResponse> getProductsByCategoryIncludingChildren(Long categoryId, Pageable pageable) {
        //1. category 조회
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        //2. 해당 카테고리와 하위 카테고리의 모든 상품 조회
        return productRepository.findByCategoryPath(
                category.getPath(),
                pageable
        ).map(ProductResponse::from);
    }

    /**
     * 검색 조건에 맞는 상품을 검색하여 조회
     *
     * @param condition 검색 조건
     * @param pageable  페이지 정보
     * @return 검색 조건에 맞는 상품 목록을 페이징하여 ProductResponse DTO로 변환한 결과
     */
    public Page<ProductResponse> searchProducts(ProductSearchCondition condition, Pageable pageable) {
        Page<ProductResponse> productResponses = productSearchRepo.searchProducts(condition, pageable);

        // productResponses에서 상품 ID를 추출
        List<Long> productIds = productResponses.getContent().stream()
                .map(ProductResponse::getId)
                .toList();


        // Redis에서 조회수와 좋아요수를 가져오는 메서드 호출
        List<Map<Object, Object>> values = getViewCountFromRedis(productIds);

        // Redis에서 가져온 조회수와 좋아요수를 productResponses에 병합
        List<ProductResponse> updatedContent = mergeProductResponses(productResponses.getContent(), values);

        return new PageImpl<>(updatedContent, pageable, productResponses.getTotalElements());
    }


    /**
     * @param id 조회할 상품의 ID
     * @return 해당 상품의 상세 정보를 ProductDetailResponse DTO로 변환한 결과
     * @throws IllegalArgumentException 상품 ID가 존재하지 않는 경우
     * @apiNote 특정 상품 ID에 따른 상품 상세 정보 조회
     */
    public ProductDetailResponse getProductById(Long id) {

//        incrementViewCount(id);// 조회수 증가
        incrementViewCountRedis(id);// Redis 조회수 증가

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());
        List<PriceInfo> priceInfoList = priceInfoRepository.findByProductIdOrderByPriceAsc(id);

        return ProductDetailResponse.from(priceInfoList, product);
    }

    /**
     * @param id 삭제할 상품의 ID
     * @apiNote 특정 ID의 상품을 삭제
     */
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
        productRepository.deleteById(id);
    }

    /**
     * @param laptopRequest 노트북 상품 정보
     * @return 생성된 노트북 상품 정보
     * @apiNote 노트북 상품 생성
     */
    public Product createLaptop(LaptopRequest laptopRequest) {
        Category category = categoryRepository.findById(laptopRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        return productRepository.save(laptopRequest.toEntity(laptopRequest, category));
    }

    /**
     * @param tvRequest 텔레비전 상품 정보
     * @return 생성된 텔레비전 상품 정보
     * @apiNote 텔레비전 상품 생성
     */
    public Product createTV(TvRequest tvRequest) {
        Category category = categoryRepository.findById(tvRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        return productRepository.save(tvRequest.toEntity(tvRequest, category));
    }

    /**
     * @param furnitureRequest 가구 상품 정보
     * @return 생성된 가구 상품 정보
     * @apiNote 가구 상품 생성
     */
    public Product createFurniture(FurnitureRequest furnitureRequest) {
        Category category = categoryRepository.findById(furnitureRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        return productRepository.save(furnitureRequest.toEntity(furnitureRequest, category));
    }


    /**
     * @apiNote (ConcurrentHashMap 사용)
     * 조회수 증가 (메모리 캐시에서 관리)
     */
    public void incrementViewCount(Long productId) {
        viewCountCache.computeIfAbsent(productId, id -> new AtomicLong(0)).incrementAndGet();
    }

    /**
     * @apiNote (Redis 사용)
     * 조회수 증가 (캐시에서 관리)
     */
    public void incrementViewCountRedis(Long productId) {
        redisTemplate.opsForHash().increment("product:" + productId, "viewCount", 1L);
    }

    /**
     * @apiNote concurrentHashMap 에서 조회수 가져오기
     */
    public Long getViewCount(Long productId) {
        return viewCountCache.getOrDefault(productId, new AtomicLong(0)).get();
    }


    /**
     * @apiNote Redis에서 조회수와 좋아요수를 가져오기
     */
    public List<Map<Object, Object>> getViewCountFromRedis(List<Long> productIds) {
        List<String> keys = productIds.stream()
                .map(productId -> "product:" + productId)
                .collect(Collectors.toList());

        List<Map<Object, Object>> redisValues = keys.stream()
                .map(key -> redisTemplate.opsForHash().entries(key))
                .toList();

        return redisValues;
    }

    /**
     * @apiNote Redis에서 가져온 조회수와 좋아요수를 ProductResponse에 병합
     * @param content
     * @param values
     * @return
     */
    private List<ProductResponse> mergeProductResponses(List<ProductResponse> content, List<Map<Object, Object>> values) {
        return IntStream.range(0, content.size())
                .mapToObj(i -> {
                    ProductResponse productResponse = content.get(i);
                    Map<Object, Object> value = values.get(i);
                    //String to Long
                    Long viewCount = Long.valueOf((String)value.getOrDefault("viewCount", 0L + ""));
                    Long likeCount = Long.valueOf((String)value.getOrDefault("likeCount", 0L + ""));
                    // 조회수와 좋아요수를 ProductResponse에 설정
                    productResponse.setViewCount(viewCount);
                    productResponse.setLikeCount(likeCount);

                    return productResponse;
                }).collect(Collectors.toList());
    }


    /**
     * @apiNote 주기적으로 DB에 반영 (Scheduled Task)
     */
//    @Scheduled(fixedRate = 60000) // 10초마다 DB 업데이트
    public void syncViewCountsToDB() {
        for (Iterator<Map.Entry<Long, AtomicLong>> it = viewCountCache.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, AtomicLong> entry = it.next();
            long viewCount = entry.getValue().getAndSet(0);
            if (viewCount > 0) {
                productRepository.updateViewCount(entry.getKey(), viewCount);
            } else {
                it.remove(); // 조회수 0이면 캐시에서 제거
            }
        }
    }

}
