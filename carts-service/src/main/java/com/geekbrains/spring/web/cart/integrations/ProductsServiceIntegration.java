package com.geekbrains.spring.web.cart.integrations;

import com.geekbrains.spring.web.api.model.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final RestTemplate restTemplate;

    @Value("${integrations.core-service.url}")
    private String productServiceUrl;

//    public Optional<ProductDto> findById(Long id) {
//        ProductDto productDto = restTemplate.getForObject(productServiceUrl + "/api/v1/products/" + id, ProductDto.class);
//        return Optional.ofNullable(productDto);
//    }

    public MessageResponse findById(Long id) {
        try {
            MessageResponse productDto = restTemplate.getForObject(
                    productServiceUrl + "/api/v1/products/exceptions/" + id, MessageResponse.class);
            return productDto;
        }catch (NoSuchElementException e) {
            MessageResponse messageResponse = new MessageResponse(null, 404, false, "Product not found, id: "  + id);
            return messageResponse;
        }catch(HttpClientErrorException.BadRequest e ){
            MessageResponse messageResponse = new MessageResponse(null, 400, false, "Bad Request");
            return messageResponse;
        }catch (HttpServerErrorException.InternalServerError e) {
            MessageResponse messageResponse = new MessageResponse(null, 500, false, "Internal Server Error");
            return messageResponse;
        }
    }
}
