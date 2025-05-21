package com.example.CompProductSystem.api.Like;

import com.example.CompProductSystem.api.Member.Member;
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

}
