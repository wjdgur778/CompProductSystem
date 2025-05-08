package com.example.CompProductSystem.api.Product.dto.request;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.TV;

public class TvRequest extends ProductRequest{
    Double inch;
    public TV toEntity(TvRequest tvRequest, Category category) {
        return TV.builder()
                .name(tvRequest.getName())
                .imageUrl(tvRequest.getImageUrl())
                .inch(tvRequest.inch)
                .category(category)
                .build();
    }


}
