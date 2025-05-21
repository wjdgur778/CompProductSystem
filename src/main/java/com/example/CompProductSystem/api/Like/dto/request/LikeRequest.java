package com.example.CompProductSystem.api.Like.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeRequest {
    private Long memberId;
    private Long productId;

    public LikeRequest(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }
}
