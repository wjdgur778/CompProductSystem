package com.example.CompProductSystem.api.Product.dto.response;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.PriceInfo.PriceInfo;
import com.example.CompProductSystem.api.Product.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

/**
 * 상품이 보여지는 하나의 컴포넌트를 위한 데이터
 * 상품이름, 등록 월, 이미지링크, 쇼핑몰별 가격정보리스트(제휴 링크, 가격, 배달비)
 */
@Getter
@Builder
public class ProductResponse {
    private final String name;
    private final String categoryPath;
    private LocalTime releaseDate;// 등록 월
    private String imageUrl; // s3를 활용한 이미지 링크 필드
    private List<PriceInfo> priceInfo; //

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .categoryPath(product.getCategory().getPath())
                .imageUrl("")//todo 이미지 링크 넣어야해
                .priceInfo(product.getPriceInfo())
                .releaseDate(product.getReleaseDate())
                .build();
    }
}
