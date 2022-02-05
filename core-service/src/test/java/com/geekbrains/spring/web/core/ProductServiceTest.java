package com.geekbrains.spring.web.core;

import com.geekbrains.spring.web.core.dto.ProductDto;
import com.geekbrains.spring.web.core.entities.Category;
import com.geekbrains.spring.web.core.repositories.ProductsRepository;
import com.geekbrains.spring.web.core.services.ProductsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductsService productService;

    @MockBean
    private ProductsRepository productRepository;

    private static ProductDto product;

    @Test
    public void findPriceByIdTest() {
        ProductDto product = new ProductDto(1L, "Bread", 100, "Grocery");

        Mockito.doReturn(Optional.of(product)).when(productRepository).findById(1L);

        productService.findById(1L).get();
        Assertions.assertEquals(100, product.getPrice());
        Mockito.verify(productRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(1L));
    }

    @Test
    public void findCategoryByIdTest() {
        ProductDto product = new ProductDto(1L, "Bread", 100, "Grocery");

        Mockito.doReturn(Optional.of(product)).when(productRepository).findById(1L);

        productService.findById(1L).get();
        Assertions.assertEquals("Grocery", product.getCategoryTitle());
        Mockito.verify(productRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(1L));
    }
}