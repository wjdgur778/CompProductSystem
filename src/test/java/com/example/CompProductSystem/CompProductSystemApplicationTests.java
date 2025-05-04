package com.example.CompProductSystem;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Category.CategoryRepository;
import com.example.CompProductSystem.api.Member.MemberRepository;
import com.example.CompProductSystem.api.Member.MemberService;
import com.example.CompProductSystem.api.PriceInfo.PriceInfo;
import com.example.CompProductSystem.api.PriceInfo.Repository.PriceInfoRepository;
import com.example.CompProductSystem.api.PriceInfo.Service.PriceInfoService;
import com.example.CompProductSystem.api.PriceInfo.dto.request.PriceInfoRequest;
import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.api.Product.ProductController;
import com.example.CompProductSystem.api.Product.Repository.ProductRepository;
import com.example.CompProductSystem.api.Product.ProductService;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Laptop;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Furniture;
import com.example.CompProductSystem.api.Product.Repository.ProductSearchRepo;
import com.example.CompProductSystem.api.Product.Search.dto.ProductSearchCondition;
import com.example.CompProductSystem.api.Product.dto.response.ProductResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


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
    ProductSearchRepo productSearchRepo;
    @Autowired
    ProductController productController;
    @Autowired
    PriceInfoService priceInfoService;
    @Autowired
    PriceInfoRepository priceInfoRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    @Commit
    void getProductsByCategoryIncludingChildren_test() throws Exception {
        // 루트 카테고리 생성
        Category laptopCategory = new Category("노트북", null, null);
        Category furnitureCategory = new Category("가구", null, null);
        categoryRepository.save(laptopCategory);
        categoryRepository.save(furnitureCategory);

        // 하위 카테고리 생성
        Category gamingLaptopCategory = new Category("게이밍노트북", laptopCategory.getId(), laptopCategory.getPath());
        Category businessLaptopCategory = new Category("사무용노트북", laptopCategory.getId(), laptopCategory.getPath());
        categoryRepository.save(gamingLaptopCategory);
        categoryRepository.save(businessLaptopCategory);

        Category chairCategory = new Category("의자", furnitureCategory.getId(), furnitureCategory.getPath());
        categoryRepository.save(chairCategory);

        Category officeChairCategory = new Category("사무용의자", chairCategory.getId(), chairCategory.getPath());
        categoryRepository.save(officeChairCategory);

        // 각 리프 카테고리에 50개의 제품 생성 및 가격 정보 추가
        for (int i = 0; i < 100; i++) {

            // 게이밍 노트북 생성
            Laptop gamingProduct = Laptop.builder()
                    .name("Gaming Laptop " + i)
                    .category(gamingLaptopCategory)
                    .releaseDate(LocalTime.now())
                    .inch(14.1 + (i * 0.2))
                    .build();
            Product savedGamingProduct = productRepository.save(gamingProduct);
            em.flush();
            System.out.println(savedGamingProduct.getId());
            for (int j = 0; j < 10; j++) {
                // 게이밍 노트북의 가격 정보 추가
                priceInfoService.createPriceInfo(PriceInfoRequest.builder()
                        .price(1000000L + j * 10000)
                        .productId(savedGamingProduct.getId())
                        .deliveryFee(3000)
                        .ShopName("Test Shop")
                        .linkUrl("https://test.com/gaming-laptop-" + j)
                        .build());
            }


            // 사무용 노트북 생성
            Laptop businessProduct = Laptop.builder()
                    .name("Business Laptop " + i)
                    .category(businessLaptopCategory)
                    .inch(14.1 + (i * 0.2))
                    .build();
            Product savedBusinessProduct = productRepository.save(businessProduct);
            em.flush();
            for (int j = 0; j < 10; j++) {
                // 사무용 노트북의 가격 정보 추가
                priceInfoService.createPriceInfo(PriceInfoRequest.builder()
                        .price(800000L + j * 10000)
                        .productId(savedBusinessProduct.getId())
                        .deliveryFee(2000)
                        .ShopName("Test Shop")
                        .linkUrl("https://test.com/business-laptop-" + j)
                        .build());
            }


            // 사무용 의자 생성
            Furniture officeChairProduct = Furniture.builder()
                    .name("Office Chair " + i)
                    .category(officeChairCategory)
                    .build();
            Product savedOfficeChair = productRepository.save(officeChairProduct);
            em.flush();

            // 사무용 의자의 가격 정보 추가
            priceInfoService.createPriceInfo(PriceInfoRequest.builder()
                    .price(200000L + i * 1000)
                    .deliveryFee(1000)
                    .productId(savedOfficeChair.getId())
                    .ShopName("Test Shop")
                    .linkUrl("https://test.com/office-chair-" + i)
                    .build());


        }

        em.flush();
        em.clear();

        int iterations = 3;
        long totalTime = 0;

        PageRequest pageRequest = PageRequest.of(0, 50);
        ProductSearchCondition productSearchCondition = new ProductSearchCondition("LAPTOP", "노트북", null, 14.0, 40.0);
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();
//            List<Product> products = productRepository.findByCategoryPath(laptopCategory.getPath());
//            Page<ProductResponse> products = productService.getProductsByCategoryIncludingChildren(laptopCategory.getId(),pageRequest);
            Page<ProductResponse> products = (Page<ProductResponse>) productController.searchProducts(productSearchCondition, pageRequest).getBody().getData();
//            Page<ProductResponse> products = (Page<ProductResponse>)productController.searchProducts(productSearchCondition,pageRequest).getBody().getData();

            long endTime = System.nanoTime();
            long executionTime = (endTime - startTime) / 1_000_000;
            totalTime += executionTime;
            for (ProductResponse p : products) {

                System.out.print("가격 : " + p.getLowestPrice()+ " ");
            }
            System.out.println();
            System.out.println("실행 " + (i + 1) + ": " + executionTime + "ms");
            em.flush();
            em.clear();
        }

        double averageTime = (double) totalTime / iterations;
        System.out.println("평균 실행 시간: " + averageTime + "ms");

    }
}




