package com.example.CompProductSystem;

import com.example.CompProductSystem.api.Category.Category;
import com.example.CompProductSystem.api.Category.CategoryRepository;
import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.Member.MemberRepository;
import com.example.CompProductSystem.api.Member.MemberService;
import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.api.Product.ProductRepository;
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
    TestDataInitializer initData;

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
    void N_1_문제_확인() throws Exception {

        //멤버 전체 조회
        List<Member> members = memberRepository.findAll();

//		각 멤버가 가지고 있는 상품의 갯수 조회
        for (Member m : members) {
            for (int i = 0; i < m.getProducts().size(); i++) {
                System.out.println(m.getProducts().get(i).getName());
            }
//			System.out.println(m.getId()+" 번 member의 product size : "+m.getProducts().size());
        }

//		List<Product> products = productRepository.findAllWithMember();
//		for(Product p : products){
//			p.getMember().getName();
//		}
    }
    @Test
    @Transactional
    void 카테고리확인() {

        Category root = new Category();
        root.setName("컴퓨터 및 노트북");
        Category gaming = new Category();
        gaming.setName("게이밍 노트북");
        gaming.setParent(root);
        Category aGame = new Category();
        aGame.setName("A게임 권장 노트북");
        aGame.setParent(gaming);
        Category bGame = new Category();
        bGame.setName("B게임 권장 노트북");
        bGame.setParent(gaming);

        root.setParent(null);

        root.addChildCategory(gaming);
        gaming.addChildCategory(aGame);
        gaming.addChildCategory(bGame);

        categoryRepository.save(root);
        categoryRepository.save(gaming);
        categoryRepository.save(aGame);
        categoryRepository.save(bGame);

        em.flush();
        em.clear();

        List<Category> categories = categoryRepository.findByParentId(1l);
        System.out.println(categories.size());

    }
}




