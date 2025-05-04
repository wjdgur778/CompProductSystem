package com.example.CompProductSystem.api.PriceInfo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPriceInfo is a Querydsl query type for PriceInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPriceInfo extends EntityPathBase<PriceInfo> {

    private static final long serialVersionUID = 1734459740L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPriceInfo priceInfo = new QPriceInfo("priceInfo");

    public final NumberPath<Integer> deliveryFee = createNumber("deliveryFee", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath linkUrl = createString("linkUrl");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final com.example.CompProductSystem.api.Product.QProduct product;

    public final StringPath ShopName = createString("ShopName");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QPriceInfo(String variable) {
        this(PriceInfo.class, forVariable(variable), INITS);
    }

    public QPriceInfo(Path<? extends PriceInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPriceInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPriceInfo(PathMetadata metadata, PathInits inits) {
        this(PriceInfo.class, metadata, inits);
    }

    public QPriceInfo(Class<? extends PriceInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.example.CompProductSystem.api.Product.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

