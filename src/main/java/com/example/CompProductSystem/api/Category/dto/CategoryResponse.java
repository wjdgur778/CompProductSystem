package com.example.CompProductSystem.api.Category.dto;

import com.example.CompProductSystem.api.Category.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CategoryResponse {
    String name;
    private List<CategoryResponse> children;




    public static CategoryResponse of (Category category){
        return CategoryResponse.builder()
                .name(category.getName())
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
