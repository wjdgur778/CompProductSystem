package com.example.CompProductSystem.api.Product.Search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchCondition {
    private String categoryType; // "furniture", "laptop", "tv" 구분
    private String categoryPath;
    private String color; // furniture용
    private Double LMonitorSize; // laptop용
    private Double TMonitorSize; // tv용
}

