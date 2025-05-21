package com.example.CompProductSystem.api.Like;

import com.example.CompProductSystem.api.Like.dto.request.LikeRequest;
import com.example.CompProductSystem.common.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {
    private final LikeService likeService;

    /**
     * @apiNote 좋아요 생성
     * @param likeRequest 좋아요 생성 요청 정보
     */
     @PostMapping("/create")
     public ResponseEntity<Result> createLike(@RequestBody LikeRequest likeRequest) {
         likeService.createLike(likeRequest);
         return ResponseEntity.status(HttpStatus.OK)
                 .body(Result.builder()
                         .data(null)
                         .build()
                 );
     }
    /**
     * @apiNote 좋아요 삭제
     * @param id 삭제할 좋아요 ID
     */
     @PostMapping("/delete")
     public ResponseEntity<Result> deleteLike(@RequestBody Long id) {
         likeService.deleteLike(id);
         return ResponseEntity.status(HttpStatus.OK)
                 .body(Result.builder()
                         .data(null)
                         .build()
                 );
     }
    /**
     * @apiNote 좋아요 목록 조회
     * @param memberId 회원 ID
     */
    @GetMapping("/list/{memberId}")
    public ResponseEntity<Result> getLikeList(@PathVariable(name = "memberId") Long memberId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(likeService.getLikeList(memberId))
                        .build()
                );
    }
}
