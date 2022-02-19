package com.geekbrains.spring.web.api.model;

import com.geekbrains.spring.web.api.core.ProductDto;

public class MessageResponse {
    private ProductDto productDto;
    private int status;
    private boolean success;
    private String message;

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageResponse() {}

    public MessageResponse(ProductDto productDto, int status, boolean success, String message) {
        this.productDto = productDto;
        this.status = status;
        this.success = success;
        this.message = message;
    }
}