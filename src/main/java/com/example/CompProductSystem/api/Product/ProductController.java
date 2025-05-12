package com.example.CompProductSystem.api.Product;

import com.example.CompProductSystem.api.Product.Search.dto.ProductSearchCondition;
import com.example.CompProductSystem.api.Product.dto.request.FurnitureRequest;
import com.example.CompProductSystem.api.Product.dto.request.LaptopRequest;
import com.example.CompProductSystem.api.Product.dto.request.TvRequest;
import com.example.CompProductSystem.common.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    /**
     *
     * @apiNote 모든 상품 리스트 조회
     * @return 해당 카테고리의 상품 목록을 페이징하여 반환
     */
    @GetMapping()
    public ResponseEntity<Result> getProduct() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(null)
                        .build()
                );
    }

    /**
     * 상품 조회 API 설고 고려사항
     * 1. 조회 성능
     * - 필요한 경우에만 하위 카테고리 포함 조회
     * - 페이징 처리로 대량 데이터 처리 최적화
     * 2. 코드 구조
     * - 명확한 API 엔드포인트 구분
     * - 재사용 가능한 서비스 메서드 구현
     * 3. 확장성
     * - 새로운 카테고리 계층 추가 시 코드 수정 최소화
     * - 유연한 조회 조건 지원
     * API 엔드포인트 예시:
     * - /api/products/category/2 : 게이밍 노트북 카테고리의 상품만 조회
     * - /api/products/category/1/including-children : 노트북 카테고리와 게이밍 노트북 카테고리의 모든 상품을 조회
     */
    /**
     * @apiNote 특정 카테고리의 상품만 조회
     * @param categoryId 조회할 카테고리 ID
     * @param pageable 페이지 및 정렬 정보
     * @return 해당 카테고리의 상품 목록을 페이징하여 반환
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Result> getProductsByCategory(
            @PathVariable("categoryId") Long categoryId,
            @PageableDefault(size = 10, sort = "releaseTime", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(productService.getProductsByCategory(categoryId, pageable))
                        .build());
    }

    /**
     * @apiNote 특정 카테고리와 그 하위 카테고리의 모든 상품 조회
     * @param categoryId 조회할 최상위 카테고리 ID
     * @param pageable 페이지 및 정렬 정보
     * @return 해당 카테고리와 모든 하위 카테고리의 상품 목록을 페이징하여 반환
     */
    @GetMapping("/category/{categoryId}/including-children")
    public ResponseEntity<Result> getProductsByCategoryIncludingChildren(
            @PathVariable("categoryId") Long categoryId,
            @PageableDefault(size = 10, sort = "releaseTime", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(productService.getProductsByCategoryIncludingChildren(categoryId, pageable))
                        .build());
    }

    /**
     * @apiNote 특정 조건에 맞는 상품을 검색하여 조회
     * @param condition 검색 조건
     * @param pageable 페이지 및 정렬 정보
     * @return 검색 조건에 맞는 상품 목록을 페이징하여 반환
     */
    @PostMapping("/search")
    public ResponseEntity<Result> searchProducts(
            @RequestBody ProductSearchCondition condition,
//            @PageableDefault(size = 10, sort = "releaseTime", direction = Sort.Direction.DESC) Pageable pageable
            @PageableDefault(size = 10, sort = "lowestPrice", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(productService.searchProducts(condition, pageable))
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getProductById(@PathVariable(name = "id") Long id) {
        System.out.println(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(productService.getProductById(id))
                        .build());
    }

    
    // 생성을 위해서는 type이 필요
    /**
     * @apiNote 노트북 상품 생성
     * @param productRequest 노트북 상품 정보
     * @return 생성된 노트북 상품 정보
     */
    @PostMapping("/create/laptop")
    public ResponseEntity<Result> createLaptop(@RequestBody LaptopRequest laptopRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(productService.createLaptop(laptopRequest))
                        .build());
    }

    /**
     * @apiNote 텔레비전 상품 생성
     * @param productRequest 텔레비전 상품 정보
     * @return 생성된 텔레비전 상품 정보
     */
    @PostMapping("/create/tv")
    public ResponseEntity<Result> createTV(@RequestBody TvRequest tvRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(productService.createTV(tvRequest))
                        .build());
    }

    /**
     * @apiNote 가구 상품 생성
     * @param productRequest 가구 상품 정보
     * @return 생성된 가구 상품 정보
     */
    @PostMapping("/create/furniture")
    public ResponseEntity<Result> createFurniture(@RequestBody FurnitureRequest furnitureRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(productService.createFurniture(furnitureRequest))
                        .build());
    }

    /**
     * @apiNote 상품 삭제
     * @param id 삭제할 상품 ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data("상품이 성공적으로 삭제되었습니다.")
                        .build()
                );
    }

}
