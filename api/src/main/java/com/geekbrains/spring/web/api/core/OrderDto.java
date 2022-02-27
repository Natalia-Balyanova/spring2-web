package com.geekbrains.spring.web.api.core;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {
    private Long id;
    private String username;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;
    private String address;
    private String phone;
    private String name;
    private String surname;
    private String street;
    private String house;
    private String flat;
    private String city;
    private String district;
    private String postalCode;
    private String countryCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname;}

    public void setSurname(String surname) { this.surname = surname; }

    public String getStreet() { return street; }

    public void setStreet(String street) { this.street = street; }

    public String getHouse() { return house; }

    public void setHouse(String house) { this.house = house; }

    public String getFlat() { return flat; }

    public void setFlat(String flat) { this.flat = flat;}

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city;}

    public String getDistrict() { return district; }

    public void setDistrict(String district) { this.district = district;}

    public String getPostalCode() {return postalCode; }

    public void setPostalCode(String postalCode) { this.postalCode = postalCode;}

    public String getCountryCode() { return countryCode; }

    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public OrderDto() {
    }

    public OrderDto(Long id, String username, List<OrderItemDto> items, BigDecimal totalPrice, String address, String phone) {
        this.id = id;
        this.username = username;
        this.items = items;
        this.totalPrice = totalPrice;
        this.address = address;
        this.phone = phone;
    }

    public OrderDto(Long id, String username, List<OrderItemDto> items, BigDecimal totalPrice, String address, String phone, String name, String surname, String street, String house, String flat, String city, String district, String postalCode, String countryCode) {
        this.id = id;
        this.username = username;
        this.items = items;
        this.totalPrice = totalPrice;
        this.address = address;
        this.phone = phone;
        this.name = name;
        this.surname = surname;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.city = city;
        this.district = district;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
    }
}
