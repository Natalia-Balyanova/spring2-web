package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.model.MessageResponse;
import com.geekbrains.spring.web.core.converters.ProductConverter;
import com.geekbrains.spring.web.core.entities.Product;
import com.geekbrains.spring.web.core.repositories.ProductsRepository;
import com.geekbrains.spring.web.core.repositories.specifications.ProductsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final ProductConverter productConverter;

    public Page<Product> findAll(Integer minPrice, Integer maxPrice, String partTitle, Integer page, String categoryPart) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            spec = spec.and(ProductsSpecifications.titleLike(partTitle));
        }
        if (categoryPart != null){
            spec = spec.and(ProductsSpecifications.categoryEqual(categoryPart));
        }
        return productsRepository.findAll(spec, PageRequest.of(page - 1, 8));
    }

    public Optional<Product> findById(Long id) {
        return productsRepository.findById(id);
    }

    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

    public Product save(Product product) {
        return productsRepository.save(product);
    }

    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productsRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + productDto.getId()));
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        return product;
    }

    public MessageResponse findByIdForResponse(Long id) {
        try {
            MessageResponse messageResponse = new MessageResponse(productConverter.entityToDto(productsRepository.findById(id).get()), 200, true, "product added to cart");
            return messageResponse;
        } catch (NoSuchElementException e) {
            MessageResponse messageResponse = new MessageResponse(null, 404, false, "Product not found, id: "  + id);
            return messageResponse;
        } catch (HttpClientErrorException.BadRequest e) {
            MessageResponse messageResponse = new MessageResponse(null, 400, false, "Bad request");
            return messageResponse;
        } catch (HttpServerErrorException.InternalServerError e) {
            MessageResponse messageResponse = new MessageResponse(null, 500, false, "Internal Server Error");
            return messageResponse;
        }
    }
}