package com.insa.marketplace.mapper;

import com.insa.marketplace.dto.OrderDto;
import com.insa.marketplace.dto.OrderItemDto;
import com.insa.marketplace.model.Order;
import com.insa.marketplace.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order order);
    OrderItemDto toDto(OrderItem item);

    Order toEntity(OrderDto dto);
    OrderItem toEntity(OrderItemDto dto);
}
