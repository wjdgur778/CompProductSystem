package com.example.CompProductSystem.api.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    /**
     * @implNote 최상위 카테고리 조회
     * @return
     */
//    @Query("select c from Category c where c.parent.id = null")
    List<Category> findByParentIsNull();

    /**
     * @implNote 특정 카테고리의 하위 카테고리 조회
     * @param parentId
     * @return
     */
    @Query("select c from Category c where c.id = :parentId")
    List<Category> findByParentId(@Param("parentId") Long parentId);
}
