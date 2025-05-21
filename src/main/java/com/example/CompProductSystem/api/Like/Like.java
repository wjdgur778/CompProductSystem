package com.example.CompProductSystem.api.Like;

import com.example.CompProductSystem.api.Like.dto.request.LikeRequest;
import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.Product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "`Like`", indexes = {
        @Index(name = "idx_like_member_product", columnList = "member_id, product_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    Product product;

    @Builder
    public Like(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    public static Like from (Member member, Product product) {
        return Like.builder()
                .member(member)
                .product(product)
                .build();
    }


}

