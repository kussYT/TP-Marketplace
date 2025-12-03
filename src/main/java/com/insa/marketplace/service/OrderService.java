package com.insa.marketplace.service;

import com.insa.marketplace.dto.CheckoutItemDto;
import com.insa.marketplace.dto.CheckoutRequest;
import com.insa.marketplace.dto.OrderDto;
import com.insa.marketplace.dto.OrderItemDto;
import com.insa.marketplace.model.Order;
import com.insa.marketplace.model.OrderItem;
import com.insa.marketplace.model.Product;
import com.insa.marketplace.repository.OrderRepository;
import com.insa.marketplace.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDto> checkout(String userId, CheckoutRequest request) {
        List<CheckoutItemDto> items = request.getItems();
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }

        // 1) Récupérer tous les produits concernés
        List<String> productIds = items.stream()
                .map(CheckoutItemDto::getProductId)
                .distinct()
                .toList();

        List<Product> products = productRepository.findAllById(productIds);

        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        // 2) Construire les OrderItem et regrouper par producerId
        Map<String, List<OrderItem>> itemsByProducer = new HashMap<>();

        for (CheckoutItemDto itemDto : items) {
            Product product = productMap.get(itemDto.getProductId());
            if (product == null) {
                // produit introuvable → on peut ignorer ou lancer une exception
                continue;
            }

            double price = product.getPrice();
            OrderItem orderItem = new OrderItem(
                    product.getId(),
                    itemDto.getQuantity(),
                    price
            );

            String producerId = product.getProducerId();
            itemsByProducer
                    .computeIfAbsent(producerId, k -> new ArrayList<>())
                    .add(orderItem);
        }

        // 3) Créer une Order par producteur
        List<Order> ordersToSave = new ArrayList<>();

        for (Map.Entry<String, List<OrderItem>> entry : itemsByProducer.entrySet()) {
            String producerId = entry.getKey();
            List<OrderItem> orderItems = entry.getValue();

            Order order = new Order();
            order.setUserId(userId);
            order.setProducerId(producerId);
            order.setCreatedAt(LocalDateTime.now());
            order.setItems(orderItems);

            ordersToSave.add(order);
        }

        // 4) Sauvegarder et renvoyer en DTO
        List<Order> savedOrders = orderRepository.saveAll(ordersToSave);

        return savedOrders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersForUser(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private OrderDto toDto(Order order) {
        List<OrderItemDto> itemDtos = order.getItems().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return new OrderDto(
                order.getId(),
                order.getUserId(),
                order.getProducerId(),
                order.getCreatedAt(),
                itemDtos
        );
    }

    private OrderItemDto toDto(OrderItem item) {
        return new OrderItemDto(
                item.getProductId(),
                item.getQuantity(),
                item.getPrice()
        );
    }
}
