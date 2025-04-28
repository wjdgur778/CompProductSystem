package com.example.CompProductSystem.api.Product;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Category.CategoryRepository;
import com.example.CompProductSystem.api.Product.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

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
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        return productRepository.findByCategoryPath(
                category.getPath(),
                pageable
        ).map(ProductResponse::from);
    }



}
