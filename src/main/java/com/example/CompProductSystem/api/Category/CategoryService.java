package com.example.CompProductSystem.api.Category;

import com.example.CompProductSystem.api.Category.dto.request.CategoryRequest;
import com.example.CompProductSystem.api.Category.dto.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
        return categoryRepository.findByParentIsNull().stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    /**
     * @apiNote  하위 카테고리 조회
     * @param categoryId
     * @return CategoryResponse
     */
    public List<CategoryResponse> getChildCategories(Long categoryId) {

        return categoryRepository.findByParentId(categoryId).stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    /**
     * @apiNote 최상위 카테고리 생성
     * @param categoryRequest
     */
    public void createTopCategory(CategoryRequest categoryRequest) {
        categoryRepository.save(CategoryRequest.toEntity(categoryRequest));
    }

    /**
     * @apiNote 하위 카테고리 생성
     * @param categoryRequest
     */
    public void createDownCategory(CategoryRequest categoryRequest) {
        categoryRepository.save(CategoryRequest.toEntity(categoryRequest));
    }
}
