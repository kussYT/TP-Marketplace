package com.insa.marketplace.mapper;

import com.insa.marketplace.dto.ProductDto;
import com.insa.marketplace.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);
    Product toEntity(ProductDto dto);
}
