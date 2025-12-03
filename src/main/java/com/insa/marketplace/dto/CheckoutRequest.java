package com.insa.marketplace.dto;

import java.util.List;

public class CheckoutRequest {

    private List<CheckoutItemDto> items;

    public CheckoutRequest() {
    }

    public CheckoutRequest(List<CheckoutItemDto> items) {
        this.items = items;
    }

    public List<CheckoutItemDto> getItems() {
        return items;
    }

    public void setItems(List<CheckoutItemDto> items) {
        this.items = items;
    }
}
