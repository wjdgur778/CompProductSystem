package com.example.CompProductSystem.api.Product;

import com.example.CompProductSystem.api.Member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    Member member;

//    외부에서 사용할 수 없도록
//    private 설정을 해주는 것이 좋다.
//    validation을 거치는 메쏘드에서 private setMember를 부르는 식으로 짜면
//    좋을듯.
    public void setMember(Member member){
        this.member = member;
    }

    @Builder
    public Product(String name){
        this.name = name;
    }


}
