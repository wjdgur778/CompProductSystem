package com.example.CompProductSystem.api.Category;

import com.example.CompProductSystem.api.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * @implNote 최상위 카테고리 조회
     * @return
     */
    @Query("select c from Category c where c.parentId is null")
    List<Category> findRootCategories();

    Optional<Category>  findByName(String name);

    /**
     * @implNote 특정 카테고리의 하위 카테고리 조회
     * @param parentId
     * @return
     */
    @Query("select c from Category c where c.parentId = :parentId")
    List<Category> findChildCategories(@Param("parentId") Long parentId);

    @Query("select c from Category c where c.id = :id")
    Category findByIdWithParent(@Param("id") Long id);

    @Query("select p from Product p where p.category.id = :categoryId")
    List<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId);
}
