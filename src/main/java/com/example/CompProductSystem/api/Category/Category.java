package com.example.CompProductSystem.api.Category;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "CATEGORY",indexes ={
        @Index(name = "idx_path", columnList = "path")
    }
)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(nullable = false)
    private String path;

    @Builder
    public Category(String name, Long parentId, String parentPath) {
        this.name = name;
        this.parentId = parentId;
        this.path = parentPath != null ? parentPath + "/" + name : name;
    }

    protected void changeName(String name) {
        this.name = name;
        if (isRoot()) {
            this.path = name;
        }
    }

    protected void changeParent(Long parentId, String parentPath) {
        this.parentId = parentId;
        this.path = parentPath + "/" + this.name;
    }

    protected boolean isRoot() {
        return parentId == null;
    }
}