package com.example.CompProductSystem.api.Product.dto.response;

import com.example.CompProductSystem.api.PriceInfo.PriceInfo;
import com.example.CompProductSystem.api.PriceInfo.dto.response.PriceInfoResponse;
import com.example.CompProductSystem.api.Product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Getter
public class ProductDetailResponse {
    private ProductResponse productResponse;
    private List<PriceInfoResponse> priceInfoResponseList;

    public static ProductDetailResponse from(List<PriceInfo> priceInfoList,Product product){
        return ProductDetailResponse.builder()
                .priceInfoResponseList(PriceInfoResponse.toListFrom(priceInfoList))
                .productResponse(ProductResponse.from(product))
                .build();
    }
}
