package com.example.CompProductSystem.api.Product.Repository;

import com.example.CompProductSystem.api.Product.Product;
import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.*;
import com.example.CompProductSystem.api.Product.Search.dto.ProductSearchCondition;
import com.example.CompProductSystem.api.Product.dto.response.ProductResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductSearchRepo {
    public final JPAQueryFactory queryFactory;

    /**
     * condition에 조건에 필요한 파라미터들을 만들어 조건에 맞는 상품들을 조회한다.
     * 카테고리 타입에 따라 조회하는 메서드를 분기처리한다.     
     * 카테고리 타입은 대문자로 저장되어 있기 때문에 비교할때는 대소문자 구분없이 비교한다.
     */
    public Page<ProductResponse> searchProducts(ProductSearchCondition condition, Pageable pageable) {
        if ("FURNITURE".equalsIgnoreCase(condition.getCategoryType())) {
            return searchFurniture(condition, pageable);
        } else if ("LAPTOP".equalsIgnoreCase(condition.getCategoryType())) {
            return searchLaptop(condition, pageable);
        } else if ("TV".equalsIgnoreCase(condition.getCategoryType())) {
            return searchTV(condition, pageable);
        } else {
            throw new IllegalArgumentException("알 수 없는 카테고리입니다.");
        }
    }

    /**
     * 
     */
    private Page<ProductResponse> searchFurniture(ProductSearchCondition condition, Pageable pageable) {
        QFurniture furniture = QFurniture.furniture;

        List<ProductResponse> contents = queryFactory
                .selectFrom(furniture)
                .where(colorEq(condition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());

        Long total = queryFactory
                .select(furniture.count())
                .from(furniture)
                .where(colorEq(condition))
                .fetchOne();
        return new PageImpl<>(contents, pageable, total == null ? 0 : total);
    }

    /**
     * @param condition
     * @param pageable
     * @return
     */
    private Page<ProductResponse> searchLaptop(ProductSearchCondition condition, Pageable pageable) {
        QLaptop laptop = QLaptop.laptop;

        List<ProductResponse> contents = queryFactory
                .selectFrom(laptop)
                .where(monitorSizeCondition(condition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());

        Long total = queryFactory
                .select(laptop.count())
                .from(laptop)
                .where(monitorSizeCondition(condition))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }


    private Page<ProductResponse> searchTV(ProductSearchCondition condition, Pageable pageable) {
        QTV tv = QTV.tV;
        List<ProductResponse> contents = queryFactory
                .selectFrom(tv)
                .where(inchesCondition(condition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());

        Long total = queryFactory
                .select(tv.count())
                .from(tv)
                .where(inchesCondition(condition))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

   
    /**
     * 색상 조건에 맞는 상품을 조회하는 메서드
     * @param condition 검색 조건
     * @return 검색 조건에 맞는 상품 목록
     */
    private BooleanExpression colorEq(ProductSearchCondition condition) {
        QFurniture furniture = QFurniture.furniture;
        String color = condition.getColor();
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(furniture.categoryPath.like(condition.getCategoryPath() + "%"));
        return color != null ? furniture.color.eq(color) : null;
    }

    /**
     * 노트북 모니터 크기 조건에 맞는 상품을 조회하는 메서드
     * @param condition 검색 조건
     * @return 검색 조건에 맞는 상품 목록
     */
    private BooleanBuilder monitorSizeCondition(ProductSearchCondition condition) {
        QLaptop laptop = QLaptop.laptop;
        Double inch = condition.getMonitorSize();
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(laptop.categoryPath.like(condition.getCategoryPath() + "%"));
        //14인치대
        if (inch >= 14d && inch < 15d)
            builder.and(laptop.inch.goe(14d)
                    .and(laptop.inch.lt(15d)));
        //15인치대
        if (inch >= 15d && inch < 16d)
            builder.and(laptop.inch.goe(15d)
                    .and(laptop.inch.lt(16d)));
        //16인치대
        if (inch >= 16d && inch < 17d)
            builder.and(laptop.inch.goe(16d)
                    .and(laptop.inch.lt(17d)));
        //17인치이상
        if (inch > 17d)
            builder.and(laptop.inch.goe(17d));
        return builder;
    }

    /**
     * tv 모니터 크기 조건에 맞는 상품을 조회하는 메서드
     * @param condition 검색 조건
     * @return 검색 조건에 맞는 상품 목록
     */
    private BooleanBuilder inchesCondition(ProductSearchCondition condition) {
        QTV tv = QTV.tV;
        BooleanBuilder builder = new BooleanBuilder();
        Double inch = condition.getInches();
        builder.and(tv.categoryPath.like(condition.getCategoryPath() + "%"));
        //40인치대
        if (inch >= 40d && inch < 50d)
            builder.and(tv.inch.goe(14d)
                    .and(tv.inch.lt(15d)));
        //50인치대
        if (inch >= 50d && inch < 60d)
            builder.and(tv.inch.goe(15d)
                    .and(tv.inch.lt(16d)));
        //60인치대
        if (inch >= 60d && inch < 70d)
            builder.and(tv.inch.goe(16d)
                    .and(tv.inch.lt(17d)));
        //70인치이상
        if (inch > 70d)
            builder.and(tv.inch.goe(17d));
        return builder;
    }

}
