package com.example.CompProductSystem.api.Product.dto.request;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Furniture;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Laptop;
import lombok.Getter;

@Getter
public class FurnitureRequest  extends ProductRequest{
    String color;

    public Furniture toEntity(FurnitureRequest furnitureRequest, Category category) {
        return Furniture.builder()
                .name(furnitureRequest.getName())
                .imageUrl(furnitureRequest.getImageUrl())
                .color(furnitureRequest.color)
                .category(category)
                .build();
    }
}
