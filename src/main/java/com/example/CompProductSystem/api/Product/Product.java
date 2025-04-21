package com.example.CompProductSystem.api.Product;

import com.example.CompProductSystem.api.Member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

/**
 * todo. make impl _entity
 * todo. Product / class -> interface
 * todo. figure out. how compose these entities
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서는 기본 생성자를 못쓰게 하되, JPA는 접근 가능하게
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //단일 테이블 전략을 위한 어노테이션. 구체 상품 조회시에 조인이 필요없다.
@DiscriminatorColumn(name = "DTYPE") //DTYPE을 통해 product에 속한 item들을 구분한다.
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// id
    private String name;// 상품 이름
    private LocalTime releaseDate;// 등록 월
    private String imageUrl; // s3를 활용한 이미지 링크 필드
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;// 멤버와의 manytoone 관계


    /**
     * 외부에서 사용할 수 없도록
     * private 설정을 해주는 것이 좋다.
     * validation을 거치는 메쏘드에서 private setMember를 부르는 식으로 짜면
     * 좋을듯.
     * @param member
     */

    public void setMember(Member member) {
        this.member = member;
        member.getProducts().add(this);
    }
    public void setName(String name){
        this.name = name;
    }

//
//    @Builder
//    public Product(String name){
//        this.name = name;
//    }


}
