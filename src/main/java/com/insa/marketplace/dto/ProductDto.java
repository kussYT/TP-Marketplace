package com.insa.marketplace.dto;

public class ProductDto {

    private String id;
    private String producerId;
    private String name;
    private String description;
    private double price;
    private int stock;

    public ProductDto() {
    }

    public ProductDto(String id,
                      String producerId,
                      String name,
                      String description,
                      double price,
                      int stock) {
        this.id = id;
        this.producerId = producerId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
