package com.example.CompProductSystem.api.PriceInfo.Service;

import com.example.CompProductSystem.api.PriceInfo.PriceInfo;
import com.example.CompProductSystem.api.PriceInfo.Repository.PriceInfoRepository;
import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.api.Product.Repository.ProductRepository;
import com.example.CompProductSystem.api.PriceInfo.dto.request.PriceInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceInfoService {
    private final PriceInfoRepository priceInfoRepository;
    private final ProductRepository productRepository;

    public void createPriceInfo(PriceInfoRequest priceInfoRequest){
        System.out.println("createPriceInfo "+priceInfoRequest.getProductId());
        // 상품 조회
        Product product = productRepository.findById(priceInfoRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        System.out.println(product.getName());
        // 가격 정보 생성
        PriceInfo priceInfo = PriceInfo.builder()
                .price(priceInfoRequest.getPrice())
                .product(product)
                .deliveryFee(priceInfoRequest.getDeliveryFee())
                .ShopName(priceInfoRequest.getShopName())
                .linkUrl(priceInfoRequest.getLinkUrl())
                .build();

        // 가격 정보 저장
        priceInfoRepository.save(priceInfo);

        // 상품의 최저가격 업데이트
        updateLowestPrice(product, priceInfoRequest.getPrice());
    }
    
    /**
     * @apiNote 가격 정보를 수정하고 상품의 최저가격을 업데이트
     * @return 수정된 가격 정보
     */
    @Transactional
    public void updatePriceInfo(PriceInfoRequest priceInfoRequest) {
        //validation
        validatePriceInfo(priceInfoRequest.getPriceInfoId());
        validateProduct(priceInfoRequest.getProductId());
        validatePrice(priceInfoRequest.getPrice());
        validateDeliveryFee(priceInfoRequest.getDeliveryFee());
        validateLinkUrl(priceInfoRequest.getLinkUrl());

        // 상품 조회
        Product product = productRepository.findById(priceInfoRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 가격 정보 조회
        PriceInfo priceInfo = priceInfoRepository.findById(priceInfoRequest.getPriceInfoId())
                .orElseThrow(() -> new IllegalArgumentException("가격 정보를 찾을 수 없습니다."));

        // 가격 정보 수정
        priceInfo.changePrice(priceInfoRequest.getPrice());
        priceInfo.changeDeliveryFee(priceInfoRequest.getDeliveryFee());
        priceInfo.changeLinkUrl(priceInfoRequest.getLinkUrl());

        // 상품의 최저가격 업데이트
        updateLowestPrice(product, priceInfoRequest.getPrice());
    
        priceInfoRepository.save(priceInfo);
    }

    /**
     * @apiNote 상품의 최저가격을 업데이트
     * @param product 상품 엔티티
     * @param price
     */
    private void updateLowestPrice(Product product, Long price) {
    // 1. DB에서 최저가격 정보 조회
    Long lowestPrice =  priceInfoRepository.findLowestPriceWithProductId(product.getId());
    // 2. 최저가격 계산
//    Long lowestPrice = priceInfos.stream()
//                .map(PriceInfo::getPrice)
//                .min(Long::compare)
//                .orElse(price);
        // 3. 상품의 최저가격 업데이트
        product.setLowestPrice(lowestPrice);
        // 4. DB에 저장
        productRepository.save(product);
    }

    private void validatePriceInfo(Long priceInfoId) {
        if (priceInfoId == null) {
            throw new IllegalArgumentException("가격 정보 ID가 없습니다.");
        }
    }

    private void validateProduct(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("상품 ID가 없습니다.");
        }
    }

    private void validatePrice(Long price) {
        if (price == null) {
            throw new IllegalArgumentException("가격이 없습니다.");
        }
    }

    private void validateDeliveryFee(Integer deliveryFee) {
        if (deliveryFee == null) {
            throw new IllegalArgumentException("배송비가 없습니다.");
        }
    }

    private void validateLinkUrl(String linkUrl) {
        if (linkUrl == null) {
            throw new IllegalArgumentException("링크 URL이 없습니다.");
        }
    }
}