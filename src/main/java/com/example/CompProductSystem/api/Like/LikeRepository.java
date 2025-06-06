package com.example.CompProductSystem.api.Like;

import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.api.Product.dto.response.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // 좋아요를 추가하는 메서드
    // JpaRepository를 상속받아 기본적인 CRUD 메서드 사용 가능
    List<Like> findByMember(Member member);

    /**
     * 단순 projection으로 조회
     * @apiNote 좋아요 상품 목록 조회
     */
    @Query("SELECT new com.example.CompProductSystem.api.Product.dto.response.ProductResponse(p.id, p.name, p.lowestPrice) " +
            "FROM Like as l JOIN l.product as p WHERE l.member.id = :memberId")
    List<ProductResponse> findLikedProductsByMemberId(@Param("memberId") Long memberId);

    /**
     * @param member 좋아요를 추가할 회원
     * @param product 좋아요를 추가할 상품
     * @return 좋아요가 존재하는지 여부
     * @apiNote 좋아요가 존재하는지 확인
     */
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Like l WHERE l.member = :member AND l.product = :product")
    boolean existsByMemberAndProduct(Member member, Product product);

}
