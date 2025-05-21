package com.example.CompProductSystem.api.Order;

import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private int quantity;


    @Builder
    protected OrderProduct(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    protected void changeMember(Member member) {
        this.member = member;
    }

    protected void changeProduct(Product product) {
        this.product = product;
    }
}
