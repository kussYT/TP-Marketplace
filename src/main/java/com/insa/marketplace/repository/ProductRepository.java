package com.insa.marketplace.repository;

import com.insa.marketplace.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByProducerId(String producerId);
}
