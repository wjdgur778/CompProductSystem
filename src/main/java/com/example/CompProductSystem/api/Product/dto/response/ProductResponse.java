package com.example.CompProductSystem.api.Product.dto.response;

import com.example.CompProductSystem.api.PriceInfo.PriceInfo;
import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Furniture;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Laptop;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.TV;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 상품이 보여지는 하나의 컴포넌트를 위한 데이터
 * 상품이름, 등록 월, 이미지링크, 쇼핑몰별 가격정보리스트(제휴 링크, 가격, 배달비)
 */
@Getter
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private LocalDateTime releaseDate;// 등록 월
    private String imageUrl; // s3를 활용한 이미지 링크 필드
    private Long lowestPrice;
    private Object details; // LaptopResponse, FurnitureResponse 등을 담기 위한 필드
    private Long viewCount;

    public static ProductResponse from(Product product) {
        Object detail=null;

        if (product instanceof Laptop laptop) {
            detail = LaptopResponse.builder()
                    .inch(laptop.getLMonitorSize())
                    .build();
        } else if (product instanceof Furniture furniture) {
            detail = FurnitureResponse.builder()
                    .color(furniture.getColor())
                    .build();
        }
        else if (product instanceof TV tv){
            detail = TvResponse.builder()
                    .inch(tv.getTMonitorSize())
                    .build();
        }
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .releaseDate(product.getReleaseTime())
                .viewCount(product.getViewCount())
                .imageUrl("")//todo 이미지 링크 넣어야해
                .lowestPrice(product.getLowestPrice())
                .details(detail)
                .build();
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    // projection을 위한 생성자
    public ProductResponse(Long id, String name, Long lowestPrice) {
        this.id = id;
        this.name = name;
        this.lowestPrice = lowestPrice;
    }

}
