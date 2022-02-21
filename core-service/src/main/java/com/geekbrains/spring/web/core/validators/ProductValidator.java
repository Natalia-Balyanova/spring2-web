package com.geekbrains.spring.web.core.validators;

import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.core.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {
    public void validate(ProductDto productDto) {
        List<String> errors = new ArrayList<>();
        if (productDto.getPrice().compareTo(BigDecimal.ONE) < 0) {
            errors.add("product's price cannot be less than 1");
        }
        if(productDto.getTitle().isBlank()) {
            errors.add("product's title cannot be empty or contain space");
        }
        if(productDto.getCategoryTitle().isBlank()) {
            errors.add("category's title cannot be empty or contain space");
        }
        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }
}