package com.example.product.product;


import java.math.BigDecimal;


public class Product {


    private Long id;

    private String name;

    private BigDecimal price;

    private Long userId;

    public Product(Long id, String name, BigDecimal price, Long userId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.userId = userId;
    }

    public Product(String name, BigDecimal price, Long userId) {
        this.name = name;
        this.price = price;
        this.userId = userId;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
