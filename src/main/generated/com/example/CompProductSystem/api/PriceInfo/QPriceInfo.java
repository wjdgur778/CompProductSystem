package com.example.CompProductSystem.api.PriceInfo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPriceInfo is a Querydsl query type for PriceInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPriceInfo extends EntityPathBase<PriceInfo> {

    private static final long serialVersionUID = 1734459740L;

    public static final QPriceInfo priceInfo = new QPriceInfo("priceInfo");

    public final NumberPath<Integer> deliveryFee = createNumber("deliveryFee", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath linkUrl = createString("linkUrl");

    public final StringPath name = createString("name");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QPriceInfo(String variable) {
        super(PriceInfo.class, forVariable(variable));
    }

    public QPriceInfo(Path<? extends PriceInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPriceInfo(PathMetadata metadata) {
        super(PriceInfo.class, metadata);
    }

}

