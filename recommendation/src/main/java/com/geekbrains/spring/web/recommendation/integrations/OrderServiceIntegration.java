package com.geekbrains.spring.web.recommendation.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderServiceIntegration {
    private final WebClient coreServiceWebClient;

    public List getOrderBetweenDate(LocalDateTime startDate, LocalDateTime finishDate) {
        return coreServiceWebClient.get()
                .uri("/api/v1/orders/between_date")
                .header("startDate", String.valueOf(startDate))
                .header("finishDate", String.valueOf(finishDate))
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }
}
