package com.example.CompProductSystem.api.Product.dto.request;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.TV;
import lombok.Getter;

@Getter
public class TvRequest extends ProductRequest{
    Double TMonitorSize;
    public TV toEntity(TvRequest tvRequest, Category category) {
        return TV.builder()
                .name(tvRequest.getName())
                .imageUrl(tvRequest.getImageUrl())
                .TMonitorSize(tvRequest.TMonitorSize)
                .category(category)
                .build();
    }


}
