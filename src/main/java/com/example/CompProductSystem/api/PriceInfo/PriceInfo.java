package com.example.CompProductSystem.api.PriceInfo;

import com.example.CompProductSystem.api.PriceInfo.dto.request.PriceInfoRequest;
import com.example.CompProductSystem.api.Product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

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
    private String ShopName;
    private Long price;
    private Integer deliveryFee;
    private String linkUrl; // 제휴 링크

    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public PriceInfo(Long price, Integer deliveryFee, String linkUrl, String ShopName,Product product) {
        this.price = price;
        this.deliveryFee = deliveryFee;
        this.linkUrl = linkUrl;
        this.product = product;
        this.ShopName = ShopName;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getPrice(){
        return this.price;
    }

    public void changePrice(Long price) {
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeDeliveryFee(Integer deliveryFee) {
        this.deliveryFee = deliveryFee;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
        this.updatedAt = LocalDateTime.now();
    }
}