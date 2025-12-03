package com.insa.marketplace.controller;

import com.insa.marketplace.dto.CheckoutRequest;
import com.insa.marketplace.dto.OrderDto;
import com.insa.marketplace.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    // POST /api/orders/checkout
    // Body :
    // {
    //   "userId": "xxx",
    //   "items": [
    //     { "productId": "p1", "quantity": 2 },
    //     { "productId": "p2", "quantity": 1 }
    //   ]
    // }
    @PostMapping("/checkout")
    public List<OrderDto> checkout(@RequestBody CheckoutRequest request) {
        return orderService.checkout(request);
    }

    // GET /api/orders/me?userId=xxx
    @GetMapping("/me")
    public List<OrderDto> getMyOrders(@RequestParam String userId) {
        return orderService.getOrdersForUser(userId);
    }
}
