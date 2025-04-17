package com.example.CompProductSystem.api.Member;

import com.example.CompProductSystem.api.Product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    //member 저장 시에 연관된 엔티티인 product를 함께 저장하기위한 cascadeType.ALL 설정
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "member",cascade = CascadeType.ALL)
    @BatchSize(size = 2)
    List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        this.products.add(product);
        product.setMember(this);
    }
    @Builder
    public Member(String name){
        this.name = name;
    }


}
