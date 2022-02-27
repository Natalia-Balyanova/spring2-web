package com.geekbrains.spring.web.core.entities;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
    CREATED(1, "Created"),
    CANCELED(2, "Canceled"),
    PAYED(3, "Payed");

    private final int id;

    private final String description;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}