package com.example.CompProductSystem.api.Product.dto.request;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Laptop;

public class LaptopRequest extends ProductRequest {
    private Double inch;

    public Laptop toEntity(LaptopRequest laptopRequest, Category category) {
        return Laptop.builder()
                .name(laptopRequest.getName())
                .imageUrl(laptopRequest.getImageUrl())
                .inch(laptopRequest.inch)
                .category(category)
                .build();
    }
}
