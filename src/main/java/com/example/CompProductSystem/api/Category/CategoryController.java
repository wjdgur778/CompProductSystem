package com.example.CompProductSystem.api.Category;

import com.example.CompProductSystem.api.Category.dto.request.CategoryRequest;
import com.example.CompProductSystem.common.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * @apiNote  최상위 카테고리 조회
     */
    @GetMapping("/top")
    public ResponseEntity<Result> getTopCategories(){

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(categoryService.getTopCategories())
                        .build());
    }

    /**
     * @apiNote  특정 카테고리의 하위 카테고리 리스트 조회
     * @param categoryId
     */
    @GetMapping("/down/{id}")
    public ResponseEntity<Result> getCategories(@PathVariable Long categoryId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(categoryService.getChildCategories(categoryId))
                        .build());
    }

    /**
     * @apiNote  최상위 카테고리 생성 , 초기 데이터 생성을 위해 단순한 구성
     * @param    parentId 를 비운다.
     * @return
     */
    @PostMapping("/top")
    public void createTopCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.createTopCategory(categoryRequest);
    }

    /**
     * @apiNote  특정 카테고리에 대한 하위 카테고리 생성, 초기 데이터 생성을 위해 단순한 구성
     * @param    parentId 를 채운다.
     * @return
     */
    @PostMapping("/down")
    public void createDownCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.createDownCategory(categoryRequest);
    }



}
