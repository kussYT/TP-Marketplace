package com.insa.marketplace.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    private String id;
    private String userId;
    private String producerId;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items;

    public OrderDto() {
    }

    public OrderDto(String id,
                    String userId,
                    String producerId,
                    LocalDateTime createdAt,
                    List<OrderItemDto> items) {
        this.id = id;
        this.userId = userId;
        this.producerId = producerId;
        this.createdAt = createdAt;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}
