package com.example.CompProductSystem.api.Product.dto.response;

import com.example.CompProductSystem.api.PriceInfo.PriceInfo;
import com.example.CompProductSystem.api.Product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Getter
public class ProductDetailResponse {
    private String name;
    private LocalDateTime releaseDate;// 등록 월
    private String imageUrl; // s3를 활용한 이미지 링크 필드
    private Long lowestPrice;
    private String ShopName;
    private Long price;
    private Integer deliveryFee;
    private String linkUrl; // 제휴 링크


    public static ProductDetailResponse from(PriceInfo priceInfo){

        return ProductDetailResponse.builder()
                .price(priceInfo.getPrice())
                .build();
    }
}
