package com.geekbrains.spring.web.recommendation.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public Integer getCountByProductId(Long productId) {
        return cartServiceWebClient.get()
                .uri("/api/v1/cart/count/" + productId)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }
}

