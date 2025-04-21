package com.example.CompProductSystem.api.Category;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // ex. 게이밍 노트북
//    연관관계를 맺으면 같은 테이블내에서 join이 일어나므로 연관관계를 맺기 보다는
//    필드에 키를 넣는 방식을 선택
    private Long parentId;

    public Category (String name,Long parentId){
        this.name = name;
        this.parentId = parentId;
    }

    public void setParent(Long parentId) {
        this.parentId = parentId;
    }
    public void setName(String name) {
        this.name = name;
    }

}