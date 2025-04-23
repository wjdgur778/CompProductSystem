package com.example.CompProductSystem.api.Product;

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
     * @return
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
        @PathVariable Long categoryId,
        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
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
        @PathVariable Long categoryId,
        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(productService.getProductsByCategoryIncludingChildren(categoryId, pageable))
                        .build());
    }

}
