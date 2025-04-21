package com.example.CompProductSystem.api.PriceInfo;

import com.example.CompProductSystem.api.Product.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;
/**
 * index 적용 방식
 */
//@Table(name = "Price_Info",indexes = {
//        @Index(name = "idx_~~",columnList = "~~")
//})
@Entity
public class PriceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //연관 관계를 맺을때 null에 대한 생각도 깊이 해봐야 한다.
    @ManyToOne
    private Product product;

//    @ManyToOne
//    private Seller seller;

    private Integer price;
    private Integer deliveryFee;
    private String linkUrl; // 제휴 링크

    private LocalDateTime updatedAt;

    public PriceInfo() {
    }
}