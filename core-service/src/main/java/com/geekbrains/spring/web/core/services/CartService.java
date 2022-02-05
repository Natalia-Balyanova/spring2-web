package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.dto.Cart;
import com.geekbrains.spring.web.core.entities.Product;

import com.geekbrains.spring.web.core.services.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductsService productsService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

//    public Cart getCurrentCart(String cartId) {
//        if (!redisTemplate.hasKey(cartPrefix + cartId)) {
//            redisTemplate.opsForValue().set(cartPrefix + cartId, new Cart());
//        }
//        Cart cart = (Cart)redisTemplate.opsForValue().get(cartPrefix + cartId);
//        return cart;
//    }

    public Cart getCurrentCart(String cartKey) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(cartKey))) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart)redisTemplate.opsForValue().get(cartKey);
    }

    public void addToCart(String cartKey, Long productId) {
        Product product = productsService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найден, id: " + productId));
        execute(cartKey, c -> {
            c.add(product);
        });
    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

//    public void save(String cartId, Cart cart) {
//        redisTemplate.opsForValue().set(cartPrefix + cartId, cart);
//    }
//
//    public void clearCart(String cartId) {
//        Cart cart = getCurrentCart(cartId);
//        cart.clear();
//        save(cartId, cart);
//    }

    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> c.remove(productId));
    }

    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> c.decrement(productId));
    }

    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}