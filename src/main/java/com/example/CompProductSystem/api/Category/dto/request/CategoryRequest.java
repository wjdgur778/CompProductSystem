package com.example.CompProductSystem.api.Category.dto.request;

import com.example.CompProductSystem.api.Category.Category;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
public class CategoryRequest {
    @NonNull
    private String name;

    @Nullable
    private Long parentId;

    @Nullable
    private String path;

    public static Category toEntity(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .parentId(categoryRequest.getParentId())
                .parentPath(categoryRequest.getPath())
                .build();
    }
}
