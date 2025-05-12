package com.example.CompProductSystem.api.Product.ProdutsDetailEntity;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.Product.Product;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA를 위한 기본 생성자
@AllArgsConstructor  // Builder를 위한 모든 필드 생성자
@DiscriminatorValue("laptop")
public class Laptop extends Product {
    private Double LMonitorSize;
//    private String manufacture; todo embedded를 통한 제조사 구별을 해야할지, 데이터 설계에 대한 고민이 필요하다.

    public void setInch(double inch){
        this.LMonitorSize = LMonitorSize;
    }

    // 부모 클래스의 필드도 포함한 생성자
    @Builder
    public Laptop(String name, String imageUrl, Member member, Category category, Double LMonitorSize) {
        super(name, imageUrl, member,category);
        this.LMonitorSize = LMonitorSize;
    }

}
