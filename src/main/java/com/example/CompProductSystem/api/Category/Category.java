package com.example.CompProductSystem.api.Category;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // ex. 게이밍 노트북

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    public void setParent(Category parent) {
        this.parent = parent;
    }
    public void addChildCategory(Category child){
        this.children.add(child);
        child.setParent(this);
    }
    public void setName(String name) {
        this.name = name;
    }

}