package com.example.CompProductSystem.api.Product.Search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductSearchCondition {
    private String categoryType; // "furniture", "laptop", "tv" 구분
    private String color; // furniture용
    private Double monitorSize; // laptop용
    private Double inches; // tv용
}

