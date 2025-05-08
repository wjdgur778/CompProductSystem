package com.example.CompProductSystem.api.Product.ProdutsDetailEntity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLaptop is a Querydsl query type for Laptop
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLaptop extends EntityPathBase<Laptop> {

    private static final long serialVersionUID = 1986834650L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLaptop laptop = new QLaptop("laptop");

    public final com.example.CompProductSystem.api.Product.QProduct _super;

    // inherited
    public final com.example.CompProductSystem.api.Category.QCategory category;

    //inherited
    public final StringPath categoryPath;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath imageUrl;

    public final NumberPath<Double> inch = createNumber("inch", Double.class);

    //inherited
    public final NumberPath<Long> lowestPrice;

    // inherited
    public final com.example.CompProductSystem.api.Member.QMember member;

    //inherited
    public final StringPath name;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> releaseTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime;

    public QLaptop(String variable) {
        this(Laptop.class, forVariable(variable), INITS);
    }

    public QLaptop(Path<? extends Laptop> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLaptop(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLaptop(PathMetadata metadata, PathInits inits) {
        this(Laptop.class, metadata, inits);
    }

    public QLaptop(Class<? extends Laptop> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.example.CompProductSystem.api.Product.QProduct(type, metadata, inits);
        this.category = _super.category;
        this.categoryPath = _super.categoryPath;
        this.id = _super.id;
        this.imageUrl = _super.imageUrl;
        this.lowestPrice = _super.lowestPrice;
        this.member = _super.member;
        this.name = _super.name;
        this.releaseTime = _super.releaseTime;
        this.updateTime = _super.updateTime;
    }

}

