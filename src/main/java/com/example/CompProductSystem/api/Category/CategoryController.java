package com.example.CompProductSystem.api.Category;

import com.example.CompProductSystem.api.Category.dto.request.CategoryRequest;
import com.example.CompProductSystem.api.Product.dto.response.ProductResponse;
import com.example.CompProductSystem.common.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * @apiNote  최상위 카테고리 조회
     */
    @GetMapping("/top")
    public ResponseEntity<Result> getTopCategories() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(categoryService.getTopCategories())
                        .build());
    }

    /**
     * @apiNote  특정 카테고리의 하위 카테고리 리스트 조회
     * @param parentId
     */
    @GetMapping("/{parentId}/child")
    public ResponseEntity<Result> getChildCategories(@PathVariable Long parentId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(categoryService.getChildCategories(parentId))
                        .build());
    }

    /**
     * @apiNote  최상위 카테고리 생성 , 초기 데이터 생성을 위해 단순한 구성
     * @param    카테고리이름,카테고리타입 / parentId 를 비운다.
     * @return
     */
    @PostMapping("/top")
    public ResponseEntity<Void> createTopCategory(@RequestBody CategoryRequest request) {
        categoryService.createTopCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @apiNote  특정 카테고리에 대한 하위 카테고리 생성, 초기 데이터 생성을 위해 단순한 구성
     * @param    카테고리이름,카테고리타입,parentId
     * @return
     */
    @PostMapping("/child")
    public ResponseEntity<Void> createChildCategory(
            @RequestBody CategoryRequest request
    ) {
        categoryService.createDownCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @apiNote  카테고리 삭제
     * @param id 삭제할 카테고리 ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
