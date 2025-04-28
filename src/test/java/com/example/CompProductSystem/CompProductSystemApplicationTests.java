package com.example.CompProductSystem;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Category.CategoryRepository;
import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.Member.MemberRepository;
import com.example.CompProductSystem.api.Member.MemberService;
import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.api.Product.ProductRepository;
import com.example.CompProductSystem.api.Product.ProductService;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Laptop;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Furniture;
import com.example.CompProductSystem.api.Product.dto.response.ProductResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
class CompProductSystemApplicationTests {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
//    TestDataInitializer initData;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    void getProductsByCategoryIncludingChildren_test() throws Exception {
        // Create root category
        Category laptopCategory = new Category("노트북", null, null);
        Category furnitureCategory = new Category("가구", null, null);
        categoryRepository.save(laptopCategory);
        categoryRepository.save(furnitureCategory);

        // Create child categories
        Category gamingLaptopCategory = new Category("게이밍노트북", laptopCategory.getId(), laptopCategory.getPath());
        Category businessLaptopCategory = new Category("사무용노트북", laptopCategory.getId(), laptopCategory.getPath());
        categoryRepository.save(gamingLaptopCategory);
        categoryRepository.save(businessLaptopCategory);

        Category chairCategory = new Category("의자", furnitureCategory.getId(), furnitureCategory.getPath());
        categoryRepository.save(chairCategory);

        Category officeChairCategory = new Category("사무용의자", chairCategory.getId(), chairCategory.getPath());
        categoryRepository.save(officeChairCategory);
//
        // Create 500 products for each leaf category
        for (int i = 0; i < 10000; i++) {

            Furniture officeChairProduct = Furniture.builder()
                    .name("Office Chair " + i)
                    .category(officeChairCategory)
                    .build();
            productRepository.save(officeChairProduct);

            Laptop gamingProduct = Laptop.builder()
                .name("Gaming Laptop " + i)
                .category(gamingLaptopCategory)
                .monitorSize(15.6)
                .build();
            productRepository.save(gamingProduct);

            Furniture ChairProduct = Furniture.builder()
                    .name("Office Chair " + i)
                    .category(chairCategory)
                    .build();
            productRepository.save(ChairProduct);

            Laptop businessProduct = Laptop.builder()
                .name("Business Laptop " + i)
                .category(businessLaptopCategory)
                .monitorSize(14.0)
                .build();
            productRepository.save(businessProduct);

        }

        em.flush();
        em.clear();

        int iterations = 3;
        long totalTime = 0;

        PageRequest pageRequest = PageRequest.of(0, 20);
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();
//            List<Product> products = productRepository.findByCategoryPath(laptopCategory.getPath());
            Page<ProductResponse> products = productService.getProductsByCategoryIncludingChildren(laptopCategory.getId(),pageRequest);
//            System.out.println("토탈 엘리먼트 "+products.getTotalElements());
//            System.out.println(products.toList().get(0).getName());
            long endTime = System.nanoTime();
            long executionTime = (endTime - startTime) / 1_000_000;
            totalTime += executionTime;

            System.out.println("실행 " + (i + 1) + ": " + executionTime + "ms");
            em.flush();
            em.clear();
        }

        double averageTime = (double) totalTime / iterations;
        System.out.println("평균 실행 시간: " + averageTime + "ms");


     }
}




