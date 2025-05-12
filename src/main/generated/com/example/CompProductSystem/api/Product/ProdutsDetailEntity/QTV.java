package com.example.CompProductSystem.api.Product.ProdutsDetailEntity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTV is a Querydsl query type for TV
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTV extends EntityPathBase<TV> {

    private static final long serialVersionUID = -961853086L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTV tV = new QTV("tV");

    public final com.example.CompProductSystem.api.Product.QProduct _super;

    // inherited
    public final com.example.CompProductSystem.api.Category.QCategory category;

    //inherited
    public final StringPath categoryPath;

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

    public final NumberPath<Double> TMonitorSize = createNumber("TMonitorSize", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime;

    //inherited
    public final NumberPath<Long> viewCount;

    public QTV(String variable) {
        this(TV.class, forVariable(variable), INITS);
    }

    public QTV(Path<? extends TV> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTV(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTV(PathMetadata metadata, PathInits inits) {
        this(TV.class, metadata, inits);
    }

    public QTV(Class<? extends TV> type, PathMetadata metadata, PathInits inits) {
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

