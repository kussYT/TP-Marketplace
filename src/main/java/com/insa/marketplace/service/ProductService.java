package com.insa.marketplace.service;

import com.insa.marketplace.dto.ProductDto;
import com.insa.marketplace.model.Product;
import com.insa.marketplace.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(p -> new ProductDto(
                        p.getId(),
                        p.getProducerId(),
                        p.getName(),
                        p.getDescription(),
                        p.getPrice(),
                        p.getStock()
                ))
                .collect(Collectors.toList());
    }
}
