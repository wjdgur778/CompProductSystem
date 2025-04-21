package com.example.CompProductSystem;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Category.CategoryRepository;
import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.Member.MemberRepository;
import com.example.CompProductSystem.api.Member.MemberService;
import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.api.Product.ProductRepository;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Laptop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    CategoryRepository categoryRepository;

    @Autowired
//    TestDataInitializer initData;

    @PersistenceContext
    EntityManager em;

    //    @BeforeEach
//    void init() {
//        initData.initData();
//        /**
//         * Member 1 .. n Product
//         *
//         *  1 - 1,2,3
//         *  2 - 4,5,6
//         *  3 - 7,8,9
//         *  4 - 10,11,12
//         *  5 - 13,14,15
//         *
//         */
//
//        //테스트가 하나의 transaction에 존재하므로 member와 product가 이미 영속성 컨텍스트에 존재할거야.
////		em.flush();
////		em.clear();
//    }
    @Test
    @Transactional
    void N_1_문제_확인() throws Exception {
// 먼저 Member 객체를 저장
        Member m1 = new Member("m1");

        Product p1 = new Laptop();
        m1.addProduct(p1); // 내부에서 setMember 수행

        Product p3 = new Laptop();
        m1.addProduct(p3); // 내부에서 setMember 수행

        memberRepository.save(m1); // 먼저 Member 저장 (연관 관계가 설정된 상태에서)

        productRepository.save(p1); // Product 저장
        productRepository.save(p3); // Product 저장

        Member m2 = new Member("m2");

        Product p2 = new Laptop();
        m2.addProduct(p2); // 내부에서 setMember 수행

        Product p4 = new Laptop();
        m2.addProduct(p4); // 내부에서 setMember 수행

        memberRepository.save(m2); // 먼저 Member 저장 (연관 관계가 설정된 상태에서)

        productRepository.save(p2); // Product 저장
        productRepository.save(p4); // Product 저장

        em.flush();
        em.clear();
        //멤버 전체 조회
//        List<Member> members = memberRepository.findAll();
        List<Product>products = productRepository.findAll();

////		각 멤버가 가지고 있는 상품의 갯수 조회
//        for (Product p : products) {
//            for (int i = 0; i <; i++) {
//                System.out.println(m.getProducts().get(i).getName());
//            }
////			System.out.println(m.getId()+" 번 member의 product size : "+m.getProducts().size());
//        }

//		List<Product> products = productRepository.findAllWithMember();
//		for(Product p : products){
//			p.getMember().getName();
//		}
    }

//    @Test
//    @Transactional
//    void 카테고리확인() {
//        System.out.println("start");
//        Category a1 = new Category();
//        a1.setName("컴퓨터 및 노트북");
//        a1.setParent(null);
//        categoryRepository.save(a1);
//
//        Category b1 = new Category();
//        b1.setName("테블릿 및 모바일");
//        b1.setParent(null);
//        categoryRepository.save(b1);
//////////////////////////////////////////////
//
//        Category notebook = new Category();
//        notebook.setName("노트북");
//        notebook.setParent(a1.getId());
//        categoryRepository.save(notebook);
//
//        Category monitor = new Category();
//        monitor.setName("모니터");
//        monitor.setParent(a1.getId());
//        categoryRepository.save(monitor);
///////////////////////////////////////////
//        Category bpc = new Category();
//        bpc.setName("브랜트 컴퓨터");
//        bpc.setParent(notebook.getId());
//        categoryRepository.save(bpc);
//
//        Category gamingnotebook = new Category();
//        gamingnotebook.setName("게이밍 노트북");
//        gamingnotebook.setParent(notebook.getId());
//        categoryRepository.save(gamingnotebook);
/////////////////////////////////////////////////////
//        Category samsungmonitor = new Category();
//        samsungmonitor.setName("삼성모니터");
//        samsungmonitor.setParent(monitor.getId());
//        categoryRepository.save(samsungmonitor);
//
//        Category applemonitor = new Category();
//        applemonitor.setName("애플모니터");
//        applemonitor.setParent(monitor.getId());
//        categoryRepository.save(applemonitor);
//
//        em.flush();
//        em.clear();
//
//        List<Category> ca =   categoryRepository.findByParentIsNull();
//        for(Category c : ca){
//            System.out.println("상위 카테 "  + c.getName());
//            List<Category> low = categoryRepository.findByParentId(c.getId());
//            for(Category cc : low){
//                System.out.println(" 하위 카테 " +cc.getName());
//            }
//        }
//
//        System.out.println();
//
//    }
//
//    @Test
//    @Transactional
//    void 상품_확인() {
//        System.out.println("start");
//        Laptop a = new Laptop();
//        a.setMonitorSize(14.7);
//        a.setName("쉠성 노트북");
//        productRepository.save(a);
//
//        Laptop b = new Laptop();
//        b.setMonitorSize(11.7);
//        b.setName("macBook");
//        productRepository.save(b);
//
//        Laptop c = new Laptop();
//        c.setMonitorSize(15.7);
//        c.setName("VivoBook");
//        productRepository.save(c);
//
//        em.flush();
//        em.clear();
//
//        productRepository.findLaptops().forEach(laptop -> System.out.println(laptop.getName()));
//    }
}




