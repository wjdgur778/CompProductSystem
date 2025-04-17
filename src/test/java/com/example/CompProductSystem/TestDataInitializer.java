package com.example.CompProductSystem;

import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.Member.MemberRepository;
import com.example.CompProductSystem.api.Product.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestDataInitializer {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    MemberRepository memberRepository;


    @Transactional
    public void initData() {
        Member m1 = new Member("m1");
        Product p1 = new Product("p1");
        Product p2 = new Product("p2");
        Product p3 = new Product("p3");

        m1.addProduct(p1);
        m1.addProduct(p2);
        m1.addProduct(p3);
        memberRepository.save(m1);

        Member m2 = new Member("m2");
        Product p4 = new Product("p4");
        Product p5 = new Product("p5");
        Product p6 = new Product("p6");
        m2.addProduct(p4);
        m2.addProduct(p5);
        m2.addProduct(p6);
        memberRepository.save(m2);

        Member m3 = new Member("m3");
        Product p7 = new Product("p7");
        Product p8 = new Product("p8");
        Product p9 = new Product("p9");
        m3.addProduct(p7);
        m3.addProduct(p8);
        m3.addProduct(p9);
        memberRepository.save(m3);

        Member m4 = new Member("m4");
        Product p10 = new Product("p10");
        Product p11 = new Product("p11");
        Product p12 = new Product("p12");
        m4.addProduct(p10);
        m4.addProduct(p11);
        m4.addProduct(p12);
        memberRepository.save(m4);

        Member m5 = new Member("m5");
        Product p13 = new Product("p13");
        Product p14 = new Product("p14");
        Product p15 = new Product("p15");
        m5.addProduct(p13);
        m5.addProduct(p14);
        m5.addProduct(p15);
        memberRepository.save(m5);

        em.flush();
        em.clear();
    }

}
