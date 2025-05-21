package com.example.CompProductSystem.api.Like;

import com.example.CompProductSystem.api.Like.dto.request.LikeRequest;
import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.Member.MemberRepository;
import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.api.Product.Repository.ProductRepository;
import com.example.CompProductSystem.api.Product.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    /**
     * @apiNote 좋아요 생성
     * @param likeRequest 좋아요 생성 요청 정보
     */
    @Transactional
    public void createLike(LikeRequest likeRequest) {
        Member member = memberRepository.findById(likeRequest.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        Product product = productRepository.findById(likeRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        // 좋아요 객체 생성
        Like like = Like.from(member,product);

        likeRepository.save(like);
    }

    /**
     * @apiNote 좋아요 삭제
     * @param id 삭제할 좋아요 ID
     */
    public void deleteLike(Long id) {
        // 좋아요 확인
        Like like = likeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 좋아요가 존재하지 않습니다."));
        // 좋아요 ID로 삭제
        likeRepository.deleteById(id);
    }

    /**
     * @apiNote 좋아요 상품 목록 조회
     * @param memberId 회원 ID
     */
    public List<ProductResponse> getLikeList(Long memberId) {
        // 회원 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        return likeRepository.findLikedProductsByMemberId(member.getId());
    }

}
