package com.example.CompProductSystem.api.Product;

import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Laptop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product p join fetch p.member")
    List<Product> findAllWithMember();

    // 1. 특정 카테고리의 상품만 조회 (페이징)
    @Query("select p from Product p where p.category.id = :categoryId")
    Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    // 2. 특정 카테고리와 그 하위 카테고리의 모든 상품 조회 (페이징)
    /**
     * LIKE 'path%'는 B-tree 인덱스를 사용할 수 있음
     * 와일드카드가 뒤에 오는 경우 인덱스 범위 스캔 가능
     * 따라서 index를 고려해 볼 필요도 있다.
     * join만 사용한 이유는 category관련 데이터는 필요 없기 때문이다.
     */
    @Query("select p from Product p where p.categoryPath like :path%")
    Page<Product> findByCategoryPath(@Param("path") String path, Pageable pageable);

}
