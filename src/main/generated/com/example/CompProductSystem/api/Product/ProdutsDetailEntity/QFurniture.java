package com.example.CompProductSystem.api.Product.ProdutsDetailEntity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFurniture is a Querydsl query type for Furniture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFurniture extends EntityPathBase<Furniture> {

    private static final long serialVersionUID = 900971762L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFurniture furniture = new QFurniture("furniture");

    public final com.example.CompProductSystem.api.Product.QProduct _super;

    // inherited
    public final com.example.CompProductSystem.api.Category.QCategory category;

    //inherited
    public final StringPath categoryPath;

    public final StringPath color = createString("color");

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath imageUrl;

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

    //inherited
    public final NumberPath<Long> viewCount;

    public QFurniture(String variable) {
        this(Furniture.class, forVariable(variable), INITS);
    }

    public QFurniture(Path<? extends Furniture> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFurniture(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFurniture(PathMetadata metadata, PathInits inits) {
        this(Furniture.class, metadata, inits);
    }

    public QFurniture(Class<? extends Furniture> type, PathMetadata metadata, PathInits inits) {
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
        this.viewCount = _super.viewCount;
    }

}

