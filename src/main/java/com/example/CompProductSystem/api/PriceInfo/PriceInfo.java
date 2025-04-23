package com.example.CompProductSystem.api.PriceInfo;

import com.example.CompProductSystem.api.Product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
/**
 * index 적용 방식
 */
//@Table(name = "Price_Info",indexes = {
//        @Index(name = "idx_~~",columnList = "~~")
//})
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PriceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;
    private Integer deliveryFee;
    private String linkUrl; // 제휴 링크

    private LocalDateTime updatedAt;

    @Builder
    protected PriceInfo(Long price, Integer deliveryFee, String linkUrl) {
        this.price = price;
        this.deliveryFee = deliveryFee;
        this.linkUrl = linkUrl;
        this.updatedAt = LocalDateTime.now();
    }

    protected void changePrice(Long price) {
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }

    protected void changeDeliveryFee(Integer deliveryFee) {
        this.deliveryFee = deliveryFee;
        this.updatedAt = LocalDateTime.now();
    }

    protected void changeLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
        this.updatedAt = LocalDateTime.now();
    }
}