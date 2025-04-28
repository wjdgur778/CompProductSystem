package com.example.CompProductSystem.api.Product.ProdutsDetailEntity;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.Product.Product;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA를 위한 기본 생성자
@DiscriminatorValue("TV")
public class TV extends Product {
    Double inches;

    @Builder
    public TV(String name, LocalTime releaseDate, String imageUrl, Member member, Category category, Double inches ) {
        super(name, releaseDate, imageUrl, member, category);
        this.inches = inches;
    }
}
