package com.geekbrains.spring.web.core;

import com.geekbrains.spring.web.core.dto.ProductDto;
import com.geekbrains.spring.web.core.entities.Category;
import com.geekbrains.spring.web.core.entities.Product;
import com.geekbrains.spring.web.core.services.CartService;
import com.geekbrains.spring.web.core.services.ProductsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@SpringBootTest
public class CartTest {
    @Autowired
    private CartService cartService;

    @MockBean
    private ProductsService productsService;

    @BeforeEach
    public void initCart() {
        cartService.clearCart("test_cart");
    }

    @Test
    public void addToCartTest() {
        Product bread = new Product(1L, "Bread", 100, "Grocery");

        Mockito.doReturn(Optional.of(bread)).when(productsService).findById(1L);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);

        Mockito.verify(productsService, Mockito.times(4)).findById(ArgumentMatchers.eq(1L));
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().size());

        //ProductDto cannot be cast to class Product. Product are in unnamed module of loader 'app'
    }
}