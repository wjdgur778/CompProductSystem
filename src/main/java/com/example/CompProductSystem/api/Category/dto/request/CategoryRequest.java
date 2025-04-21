package com.example.CompProductSystem.api.Category.dto.request;

import com.example.CompProductSystem.api.Category.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
public class CategoryRequest {
    @NonNull
    String name;

    @Nullable
    Long parentId;

    public static Category toEntity(CategoryRequest categoryRequest){
        return new Category(categoryRequest.getName(),categoryRequest.getParentId());
    }
}
