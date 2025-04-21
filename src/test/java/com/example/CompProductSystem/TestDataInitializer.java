package com.example.CompProductSystem;

import com.example.CompProductSystem.api.Member.Member;
import com.example.CompProductSystem.api.Member.MemberRepository;
import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.Laptop;
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

        em.flush();
        em.clear();
    }

}
