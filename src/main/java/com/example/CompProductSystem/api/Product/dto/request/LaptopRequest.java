package com.example.CompProductSystem.api.Product.dto.request;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Laptop;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LaptopRequest extends ProductRequest {
    @JsonProperty("LMonitorSize")
    Double LMonitorSize;

    public Laptop toEntity(LaptopRequest laptopRequest, Category category) {
        return Laptop.builder()
                .name(laptopRequest.getName())
                .imageUrl(laptopRequest.getImageUrl())
                .LMonitorSize(laptopRequest.getLMonitorSize())
                .category(category)
                .build();
    }
}
