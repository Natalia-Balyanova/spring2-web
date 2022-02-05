package com.geekbrains.spring.web.core;

import com.geekbrains.spring.web.core.dto.Cart;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CartDecrementTest {

    @Autowired
    private CartService cartService;

    @BeforeEach
    public void initCart() {
        cartService.clearCart("test_cart");
    }

    @Test
    public void decrementTest() throws Exception {
        ProductDto bread = new ProductDto(1L, "Bread", 100, "Grocery");

        cartService.addToCart("test_cart", 1L);
        cartService.decrementItem("test_cart", 1L);
        cartService.decrementItem("test_cart", 1L);
        //проверка: продукт в корзине обнуляется, а не становится "отрицательным"
//        Assertions.assertEquals(0, cartService.getCurrentCart("test_cart").getItems().size());
        Assertions.assertTrue(cartService.getCurrentCart("test_cart").getItems().isEmpty());
    }
}
