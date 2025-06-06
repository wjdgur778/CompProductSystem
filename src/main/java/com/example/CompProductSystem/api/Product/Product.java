package com.example.CompProductSystem.api.Product;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;


/**
 * todo. make impl _entity
 * todo. Product / class -> interface
 * todo. figure out. how compose these entities
 */
@Table(indexes = {
        @Index(name = "idx_path", columnList = "categoryPath")
})
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서는 기본 생성자를 못쓰게 하되, JPA는 접근 가능하게
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //단일 테이블 전략을 위한 어노테이션. 구체 상품 조회시에 조인이 필요없다.
@DiscriminatorColumn(name = "DTYPE") //DTYPE을 통해 product에 속한 item들을 구분한다.
public abstract class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// id
    private String name;// 상품 이름
    private String imageUrl; // s3를 활용한 이미지 링크 필드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;// 멤버와의 manytoone 관계

    private Long lowestPrice; // 정렬을 위한 최저가격 필드
    private Long viewCount; // 조회수
    private Long likeCount; // 좋아요 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    /*
        path를 통한 인덱스를 활용하기 위한 추가필드
     */
    @Column(name = "category_path")
    private String categoryPath;

    /**
     * 생성자 파트
     */
    protected Product(String name, String imageUrl, Member member, Category category) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.member = member;
        this.viewCount = 0l;
        this.category = category;
        this.categoryPath = category.getPath();
    }

    /**
     * 외부에서 사용할 수 없도록
     * private 설정을 해주는 것이 좋다.
     * validation을 거치는 메쏘드에서 private setMember를 부르는 식으로 짜면
     * 좋을듯.
     *
     * @param member
     */

//    public void changeMember(Member member) {
//        if (this.member != null) {
//            this.member.getProducts().remove(this);
//        }
//        this.member = member;
//        if (member != null) {
//            member.getProducts().add(this);
//        }
//    }

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void setLowestPrice(Long lowestPrice) {
        this.lowestPrice = lowestPrice;
    }


}
