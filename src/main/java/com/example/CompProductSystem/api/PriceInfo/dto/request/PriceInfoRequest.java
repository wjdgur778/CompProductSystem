package com.example.CompProductSystem.api.PriceInfo.dto.request;

import com.example.CompProductSystem.api.PriceInfo.PriceInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceInfoRequest {
    private Long productId;
    private Long priceInfoId;
    private String ShopName;
    private Long price;
    private Integer deliveryFee;
    private String linkUrl; // 제휴 링크



    static public PriceInfo toEntity(PriceInfoRequest priceInfoRequest){
        return PriceInfo.builder()
                .deliveryFee(priceInfoRequest.getDeliveryFee())
                .price(priceInfoRequest.getPrice())
                .linkUrl(priceInfoRequest.getLinkUrl())
                .ShopName(priceInfoRequest.getShopName())
                .build();
    }

}
