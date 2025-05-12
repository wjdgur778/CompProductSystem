package com.example.CompProductSystem.api.Product.Search.QuerydslUtil;

import com.example.CompProductSystem.api.Product.ProdutsDetailEntity.QLaptop;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class QuerydslUtil {
    public static OrderSpecifier<?>[] getOrderSpecifier(Sort sort, QLaptop laptop) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        // 클라이언트가 다중 정렬 요청을 보낸경우 pageable.getSort()에 여러개의 정렬조건이 있을수있다.
        //고로 for문을 통해
        for (Sort.Order order : sort) {
            PathBuilder<QLaptop> pathBuilder = new PathBuilder<>(QLaptop.class, "laptop");
            String property= order.getProperty();

            if ("lowestPrice".equals(property)) {
                orderSpecifiers.add(new OrderSpecifier<>(
                        order.isAscending() ? Order.ASC : Order.DESC,
                        pathBuilder.get("lowestPrice", BigDecimal.class)
                ));
            }
            else if("viewCount".equals(property)){
                orderSpecifiers.add(new OrderSpecifier<>(
                        order.isAscending() ? Order.ASC : Order.DESC,
                        pathBuilder.get("viewCount", BigDecimal.class)
                ));
            }

        }
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }
}
