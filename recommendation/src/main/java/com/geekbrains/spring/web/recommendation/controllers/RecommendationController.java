package com.geekbrains.spring.web.recommendation.controllers;

import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.recommendation.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

//    @GetMapping("/all")
//    public List<ProductDto> getAllProducts(){
//    }

    @GetMapping("/most_bought")
    public List<ProductDto> getMostBoughtProducts() {
        return recommendationService.getMostBoughtProducts();
    }

    @GetMapping("/most_added")
    public List<ProductDto> getMostAddedToCartProducts() {
        return recommendationService.getMostAddedToCartProducts();
    }
}
