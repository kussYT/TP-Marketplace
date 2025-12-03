package com.insa.marketplace.model;

import lombok.Data;

@Data
public class OrderItem {

    private String productId;
    private int quantity;
    private double price;
}
