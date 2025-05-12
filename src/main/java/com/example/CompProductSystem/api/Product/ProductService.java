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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductSearchRepo productSearchRepo;
    private final PriceInfoRepository priceInfoRepository;

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
        return productSearchRepo.searchProducts(condition, pageable);
    }

    /**
     * @apiNote 특정 상품 ID에 따른 상품 상세 정보 조회
     * @param id 조회할 상품의 ID
     * @return 해당 상품의 상세 정보를 ProductDetailResponse DTO로 변환한 결과
     * @throws IllegalArgumentException 상품 ID가 존재하지 않는 경우
     */
    public List<ProductDetailResponse> getProductById(Long id) {
//        Product product = priceInfos.get(0).getProduct();
        return priceInfoRepository.findAllByProductIdOrderByPriceAsc(id).stream()
                .map(ProductDetailResponse::from)
                .collect(Collectors.toList());
    }   

    /**
     * @apiNote 특정 ID의 상품을 삭제
     * @param id 삭제할 상품의 ID
     */
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                        .orElseThrow(()->new IllegalArgumentException("해당 상품이 없습니다."));
        productRepository.deleteById(id);
    }

    /**
     * @apiNote 노트북 상품 생성
     * @param laptopRequest 노트북 상품 정보
     * @return 생성된 노트북 상품 정보
     */     
    public Product createLaptop(LaptopRequest laptopRequest) {
        Category category = categoryRepository.findById(laptopRequest.getCategoryId())
                .orElseThrow(()-> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        return productRepository.save(laptopRequest.toEntity(laptopRequest,category));
    }

    /**
     * @apiNote 텔레비전 상품 생성
     * @param tvRequest 텔레비전 상품 정보
     * @return 생성된 텔레비전 상품 정보
     */
    public Product createTV(TvRequest tvRequest) {
        Category category = categoryRepository.findById(tvRequest.getCategoryId())
                .orElseThrow(()-> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        return productRepository.save(tvRequest.toEntity(tvRequest,category));
    }

    /**
     * @apiNote 가구 상품 생성
     * @param furnitureRequest 가구 상품 정보
     * @return 생성된 가구 상품 정보
     */
    public Product createFurniture(FurnitureRequest furnitureRequest) {
        Category category = categoryRepository.findById(furnitureRequest.getCategoryId())
                .orElseThrow(()-> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        return productRepository.save(furnitureRequest.toEntity(furnitureRequest,category));
    }
}
