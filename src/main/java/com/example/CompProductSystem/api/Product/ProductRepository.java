package com.example.CompProductSystem.api.Product;

import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product p join fetch p.member")
    List<Product> findAllWithMember();

    @Query("select p from Product p where type(p) = Laptop")
    List<Laptop> findLaptops();

}
