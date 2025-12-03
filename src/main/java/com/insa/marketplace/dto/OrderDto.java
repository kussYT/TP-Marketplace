package com.insa.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String id;
    private String userId;
    private String producerId;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items;
}
