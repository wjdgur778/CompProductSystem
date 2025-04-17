//package com.example.CompProductSystem.api.Order;
//
//import com.example.CompProductSystem.api.Member.Member;
//import com.example.CompProductSystem.api.Product.Product;
//import jakarta.persistence.*;
//
//@Entity
//public class OrderProduct {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    Member member;
//    @ManyToOne(fetch = FetchType.LAZY)
//    Product product;
//}
