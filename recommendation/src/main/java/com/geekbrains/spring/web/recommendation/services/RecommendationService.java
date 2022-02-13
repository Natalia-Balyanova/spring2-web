package com.geekbrains.spring.web.recommendation.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekbrains.spring.web.api.core.OrderDto;
import com.geekbrains.spring.web.api.core.OrderItemDto;
import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.recommendation.integrations.CartServiceIntegration;
import com.geekbrains.spring.web.recommendation.integrations.OrderServiceIntegration;
import com.geekbrains.spring.web.recommendation.integrations.ProductsServiceIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final ProductsServiceIntegration productServiceIntegration;
    private final CartServiceIntegration cartServiceIntegration;
    private final OrderServiceIntegration orderServiceIntegration;

    public List<ProductDto> getMostBoughtProducts() {
        List list = orderServiceIntegration.getOrderBetweenDate(
                LocalDateTime.now().minus(1, ChronoUnit.MONTHS),
                LocalDateTime.now());

        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Object order : list) {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderDto orderDto = objectMapper.convertValue(order, OrderDto.class);
            orderDtoList.add(orderDto);
        }
        List<OrderItemDto> orderItemDtoList = orderDtoList.stream()
                .map(OrderDto::getItems)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        Set<Long> productSet = orderItemDtoList.stream()
                .map(OrderItemDto::getProductId)
                .collect(Collectors.toSet());//уникальные id

        Map<Long, Integer> productMap = new HashMap();
        for (Long productId : productSet) {
            int count = 0;
            for (OrderItemDto orderItem : orderItemDtoList) {
                if (Objects.equals(orderItem.getProductId(), productId)) {
                    count = count + orderItem.getQuantity();
                }
            }
            productMap.put(productId, count);
        }
        return productMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .limit(5).map(p -> productServiceIntegration.getProductById(p.getKey())).collect(Collectors.toList());
    }

    public List<ProductDto> getMostAddedToCartProducts() {
        List<ProductDto> productDtoList = new ArrayList<>();
        List list = productServiceIntegration.getAllProducts();
        for (Object product : list) {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductDto productDto = objectMapper.convertValue(product, ProductDto.class);
            productDtoList.add(productDto);
        }

        Map<Long,Integer> productMap = new HashMap<>();
        for (ProductDto product:productDtoList) {
            Integer count = cartServiceIntegration.getCountByProductId(product.getId());
            if (count > 0){
                productMap.put(product.getId(), count);
            }
        }
        return productMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .limit(5).map(p -> productServiceIntegration.getProductById(p.getKey())).collect(Collectors.toList());
    }
}
