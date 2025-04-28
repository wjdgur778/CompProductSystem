package com.example.CompProductSystem.api.Member;

import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.common.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberRepository repository;
    @GetMapping("/")
    public ResponseEntity<Result> getMembers(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.builder()
                        .data( repository.findAll().stream()
                                .flatMap(member -> member.getProducts().stream())
                                .map(Product::getName)
                                .collect(Collectors.toList()))
                        .build()
                );
    }

}
