package com.example.CompProductSystem.api.PriceInfo.Controller;

import com.example.CompProductSystem.api.PriceInfo.Service.PriceInfoService;
import com.example.CompProductSystem.api.PriceInfo.dto.request.PriceInfoRequest;
import com.example.CompProductSystem.common.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/priceInfo")
public class PriceInfoController {
    private final PriceInfoService priceInfoService;

    /**
     * @apiNote 가격 정보 생성
     * @param priceInfoRequest 가격 정보 생성 요청 정보
     */
    @PostMapping("/create")
    public ResponseEntity<Result> createPriceInfo(@RequestBody PriceInfoRequest priceInfoRequest) {
        priceInfoService.createPriceInfo(priceInfoRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(null)
                        .build()
                );
    }

    /**
     * @apiNote 가격 정보 수정
     * @param priceInfoRequest 가격 정보 수정 요청 정보
     */
    @PostMapping("/update")
    public ResponseEntity<Result> updatePriceInfo(@RequestBody PriceInfoRequest priceInfoRequest) {
        priceInfoService.updatePriceInfo(priceInfoRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(null)
                        .build()
                );
    }

    /**
     * @apiNote 가격 삭제
     * @param id 삭제할 가격 ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteProduct(@PathVariable Long id) {
        priceInfoService.deletePriceInfo(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(null)
                        .build()
                );
    }

}
