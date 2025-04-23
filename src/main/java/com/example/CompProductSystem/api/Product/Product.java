package com.example.CompProductSystem.api.Product;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.PriceInfo.PriceInfo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;// 멤버와의 manytoone 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    /*
        todo
         OneToMany 관계를 맺을지 관계를 맺지 않고 prodcut 조회 후에 요청을 한번 더 받을지를 고민했다.
         페이징 처리를 통해 한정된 응답을 보내줄 것이며, 한번에 보여져야하는 요구사항에 맞춰 관계를 맺기로 했다.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @BatchSize(size = 5) // 배치사이즈 고려할 필요가 있다.
    private List<PriceInfo> priceInfo; //


    protected Product(String name, LocalTime releaseDate, String imageUrl, Member member, Category category) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.imageUrl = imageUrl;
        this.member = member;
        this.category = category;
    }

    /**
     * 외부에서 사용할 수 없도록
     * private 설정을 해주는 것이 좋다.
     * validation을 거치는 메쏘드에서 private setMember를 부르는 식으로 짜면
     * 좋을듯.
     * @param member
     */

    public void changeMember(Member member) {
        if (this.member != null) {
            this.member.getProducts().remove(this);
        }
        this.member = member;
        if (member != null) {
            member.getProducts().add(this);
        }
    }

    protected void changeCategory(Category category) {
        this.category = category;
    }
}
