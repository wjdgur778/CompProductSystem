package com.example.CompProductSystem.api.Product.dto.request;

import com.example.CompProductSystem.api.Product.Product;
import lombok.*;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ProductRequest {

    private String name;
    private String imageUrl; // s3를 활용한 이미지 링크 필드
    private Long categoryId;
}
