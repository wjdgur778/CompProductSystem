package com.example.CompProductSystem.api.Category.dto.response;

import com.example.CompProductSystem.api.Category.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CategoryResponse {
    private final String name;
    private final List<CategoryResponse> children;

    public static CategoryResponse of(Category category) {
        return CategoryResponse.builder()
            .name(category.getName())
            .children(null)
            .build();
    }

//
//    public static CategoryResponse fromEntity(Category category) {
//        List<CategoryResponse> childDtos = category.getChildren().stream()
//                .map(CategoryResponse::fromEntity)
//                .collect(Collectors.toList());
//        return CategoryResponse.
//    }
}
