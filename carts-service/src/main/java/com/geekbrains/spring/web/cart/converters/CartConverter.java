package com.geekbrains.spring.web.cart.converters;

import com.geekbrains.spring.web.api.cart.CartDto;
import com.geekbrains.spring.web.api.cart.CartItemDto;
import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.cart.models.Cart;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartConverter {
//    public Product dtoToEntity(ProductDto productDto) {
//        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice(), productDto.getCategoryTitle());
//    }

    public CartDto modelToDto(Cart cart) {
        List<CartItemDto> cartItemDtos = cart.getItems().stream().map(it ->
                new CartItemDto(it.getProductId(), it.getProductTitle(), it.getQuantity(), it.getPricePerProduct(), it.getPrice())
        ).collect(Collectors.toList());
        CartDto cartDto = new CartDto(cartItemDtos, cart.getTotalPrice());
        return cartDto;
    }
}
