package com.example.CompProductSystem.api.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product p join fetch p.member")
    List<Product> findAllWithMember();
}
