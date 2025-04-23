package com.example.CompProductSystem.api.Category;

import com.example.CompProductSystem.api.Category.dto.request.CategoryRequest;
import com.example.CompProductSystem.api.Category.dto.response.CategoryResponse;
import com.example.CompProductSystem.api.Product.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    /**
     * 트리 구조의 카테고리 (예: 컴퓨터 → 노트북 → AI노트북 → 인텔 NPU)
     * 각 카테고리는 상위 카테고리(Parent) 와 하위 카테고리(Children) 를 가진다.
     * 재귀적 연관관계 (@ManyToOne, @OneToMany) 를 통해 구현
     */
    private final CategoryRepository categoryRepository;


    /**
     * @apiNote  최상위 카테고리 조회
     * @return CategoryResponse
     */
    public List<CategoryResponse> getTopCategories() {
        return categoryRepository.findRootCategories().stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    /**
     * @apiNote  하위 카테고리 조회
     * @param parentId
     * @return CategoryResponse
     */
    public List<CategoryResponse> getChildCategories(Long parentId) {
        return categoryRepository.findChildCategories(parentId).stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }


    /**
     * @apiNote 최상위 카테고리 생성
     * @param request 상위 카테고리 생성 정보 요청
     */
    @Transactional
    public void createTopCategory(CategoryRequest request) {
        Category category = CategoryRequest.toEntity(request);
        categoryRepository.save(category);
    }
    
    /**
     * @apiNote 하위 카테고리 생성
     * @param request 하위 카테고리 생성 요청 정보
     */
    @Transactional
    public void createDownCategory(CategoryRequest request) {
        Category parentCategory = categoryRepository.findById(request.getParentId())
                .orElseThrow(() -> new IllegalArgumentException("상위 카테고리를 찾을 수 없습니다."));
        
        Category category = new Category(
            request.getName(),
            parentCategory.getId(),
            parentCategory.getPath()
        );
        categoryRepository.save(category);
    }

    /**
     * @apiNote 카테고리 생성 - parentId 유무에 따라 최상위/하위 카테고리 생성
     * @param request 카테고리 생성 요청 정보
     */
    @Transactional
    public void createCategory(CategoryRequest request) {
        if (request.getParentId() == null) {
            createTopCategory(request);
        } else {
            createDownCategory(request);
        }
    }

    // 카테고리 관련 메서드만 남기고 상품 조회 로직은 ProductService로 이동



}
