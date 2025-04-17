package com.example.CompProductSystem.api.Category;

import com.example.CompProductSystem.common.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoriyController {
    private final CategoryService categoryService;

    /**
     *
     * @return
     */
    @GetMapping("/top")
    public ResponseEntity<Result> getTopCategories(){

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(categoryService.getTopCategories())
                        .build());
    }

    /**
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list/{id}")
    public ResponseEntity<Result> getCategories(@PathVariable Long categoryId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data(categoryService.getChildCategories(categoryId))
                        .build());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Result> getCategorie(@PathVariable Long categoryId){
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(Result.builder()
//                        .data(categoryService.getCategories())
//                        .build());
//    }



}
