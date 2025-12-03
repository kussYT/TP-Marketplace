package com.insa.marketplace.service;

import com.insa.marketplace.dto.ProductDto;
import com.insa.marketplace.mapper.ProductMapper;
import com.insa.marketplace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * Récupère tous les produits (tous producteurs confondus)
     * et les mappe en ProductDto via MapStruct.
     */
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
