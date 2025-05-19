package com.example.CompProductSystem.api.PriceInfo.dto.response;

import com.example.CompProductSystem.api.PriceInfo.PriceInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PriceInfoResponse {
    private String shopName;
    private Long price;
    private Integer deliveryFee;
    private String linkUrl; // 제휴 링크
    private LocalDateTime updatedAt;

    public static List<PriceInfoResponse> toListFrom(List<PriceInfo> priceInfoList){
        return priceInfoList.stream()
                .map(PriceInfoResponse::from)
                .collect(Collectors.toList());
    }
    public static PriceInfoResponse from(PriceInfo priceInfo){
        return PriceInfoResponse.builder()
                .shopName(priceInfo.getShopName())
                .linkUrl(priceInfo.getLinkUrl())
                .price(priceInfo.getPrice())
                .deliveryFee(priceInfo.getDeliveryFee())
                .updatedAt(priceInfo.getUpdatedAt())
                .build();
    }
}
