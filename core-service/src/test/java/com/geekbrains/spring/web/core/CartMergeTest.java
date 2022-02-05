package com.geekbrains.spring.web.core;

import com.geekbrains.spring.web.core.dto.Cart;
import com.geekbrains.spring.web.core.entities.Category;
import com.geekbrains.spring.web.core.entities.Product;
import com.geekbrains.spring.web.core.services.CartService;
import com.geekbrains.spring.web.core.services.ProductsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

@SpringBootTest
public class CartMergeTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CartService cartService;

    @MockBean
    private ProductsService productsService;

    @BeforeEach
    public void initCart() {
//        cartService.clearCart("test_cart");
        Cart cart = new Cart();
        Product milk = new Product(3L, "Milk", 100, "Milk");
        cart.add(milk);
//        cartService.addToCart("test_cart", 3L);
        String cartKey = "test_cart";
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    @Test
    public void mergeTest() throws Exception {
        Product bread = new Product(1L, "Bread", 100, "Grocery");
        Mockito.doReturn(Optional.of(bread)).when(productsService).findById(1L);

        cartService.addToCart("guest", 1L);
        cartService.merge("test_cart", "guest");
        Assertions.assertEquals(200, cartService.getCurrentCart("test_cart").getTotalPrice());
    }
}
