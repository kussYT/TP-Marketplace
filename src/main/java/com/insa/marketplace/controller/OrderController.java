package com.insa.marketplace.controller;

import com.insa.marketplace.dto.CheckoutRequest;
import com.insa.marketplace.dto.OrderDto;
import com.insa.marketplace.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Exemple d'appel :
    // POST /api/orders/checkout?userId=123
    @PostMapping("/checkout")
    public List<OrderDto> checkout(@RequestParam String userId,
                                   @RequestBody CheckoutRequest request) {
        return orderService.checkout(userId, request);
    }

    // Exemple d'appel :
    // GET /api/orders/me?userId=123
    @GetMapping("/me")
    public List<OrderDto> getMyOrders(@RequestParam String userId) {
        return orderService.getOrdersForUser(userId);
    }
}
