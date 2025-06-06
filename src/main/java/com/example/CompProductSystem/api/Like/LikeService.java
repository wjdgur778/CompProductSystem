package com.example.CompProductSystem.api.Like;

import com.example.CompProductSystem.api.Like.dto.request.LikeRequest;
import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.Member.MemberRepository;
import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.api.Product.Repository.ProductRepository;
import com.example.CompProductSystem.api.Product.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * @param likeRequest 좋아요 생성 요청 정보
     * @apiNote 좋아요 생성
     */
    @Transactional
    public void createLike(LikeRequest likeRequest) {
        Member member = memberRepository.findById(likeRequest.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        Product product = productRepository.findById(likeRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        // 이미 좋아요가 존재하는지 확인
        if (likeRepository.existsByMemberAndProduct(member, product)) {
            throw new IllegalArgumentException("이미 좋아요가 존재합니다.");
        }
        // 좋아요 객체 생성
        Like like = Like.from(member, product);

        incrementLikeCount(product.getId());

        likeRepository.save(like);
    }

    /**
     * @param id 삭제할 좋아요 ID
     * @apiNote 좋아요 삭제
     */
    public void deleteLike(Long id) {
        // 좋아요 확인
        Like like = likeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 좋아요가 존재하지 않습니다."));
        // 좋아요 ID로 삭제
        likeRepository.deleteById(id);
    }


    /**
     * @param memberId 회원 ID
     * @apiNote 좋아요 상품 목록 조회
     */
    public List<ProductResponse> getLikeList(Long memberId) {
        // 회원 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        return likeRepository.findLikedProductsByMemberId(member.getId());
    }


    /**
     * @apiNote (Redis 사용)
     * 좋아요 수 증가 (캐시에서 관리)
     */
    public void incrementLikeCount(Long productId) {
        // redis template을 사용하여 좋아요 수 증가
        // redis에 저장된 해시맵에서 likeCount 필드의 값을 1 증가시킴
        redisTemplate.opsForHash().increment("product:" + productId, "likeCount", 1);
    }
}
