package com.example.CompProductSystem.api.PriceInfo.Repository;

import com.example.CompProductSystem.api.PriceInfo.PriceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PriceInfoRepository extends JpaRepository<PriceInfo, Long> {
    @Query("select min(pi.price) from PriceInfo pi where pi.product.id= :productId ")
    Long findLowestPriceWithProductId(@Param("productId") Long ProductId);
}