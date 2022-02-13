package com.geekbrains.spring.web.recommendation.integrations;

import com.geekbrains.spring.web.api.core.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final WebClient coreServiceWebClient;

    public List getAllProducts() {
        List productDtoList = coreServiceWebClient.get()
                .uri("/api/v1/products/all")
                .retrieve()
                .bodyToMono(List.class)
                .block();
        return productDtoList;
    }

    public ProductDto getProductById(Long productId) {
        return coreServiceWebClient.get()
                .uri("/api/v1/products/" + productId)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }
}
