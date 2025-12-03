package com.insa.marketplace.service;

import com.insa.marketplace.dto.CheckoutItemDto;
import com.insa.marketplace.dto.CheckoutRequest;
import com.insa.marketplace.dto.OrderDto;
import com.insa.marketplace.mapper.OrderMapper;
import com.insa.marketplace.model.Order;
import com.insa.marketplace.model.OrderItem;
import com.insa.marketplace.model.Product;
import com.insa.marketplace.repository.OrderRepository;
import com.insa.marketplace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    /**
     * Prend un panier (CheckoutRequest) qui contient userId + items,
     * groupe les items par producerId et crée une Order par producteur.
     */
    public List<OrderDto> checkout(CheckoutRequest request) {
        String userId = request.getUserId();
        List<CheckoutItemDto> items = request.getItems();

        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }

        // récupére tous les produits concernés
        List<String> productIds = items.stream().map(CheckoutItemDto::getProductId).distinct().collect(Collectors.toList());

        List<Product> products = productRepository.findAllById(productIds);

        Map<String, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, p -> p));

        // construire OrderItem et regrouper par producerId
        Map<String, List<OrderItem>> itemsByProducer = new HashMap<>();

        for (CheckoutItemDto itemDto : items) {
            Product product = productMap.get(itemDto.getProductId());
            if (product == null) {
                continue;
            }

            double price = product.getPrice();
            OrderItem orderItem = new OrderItem(product.getId(), itemDto.getQuantity(), price);

            String producerId = product.getProducerId();
            itemsByProducer.computeIfAbsent(producerId, k -> new ArrayList<>()).add(orderItem);
        }

        // crée une Order par producteur
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

        // sauvegarde puis mappe en DTO via MapStruct
        List<Order> savedOrders = orderRepository.saveAll(ordersToSave);

        return savedOrders.stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Récupère l'historique des commandes d'un utilisateur.
     */
    public List<OrderDto> getOrdersForUser(String userId) {
        return orderRepository.findByUserId(userId).stream().map(orderMapper::toDto).collect(Collectors.toList());
    }
}
