package com.jwt.security.domain.product;

import java.util.UUID;

public record ProductResponseDTO(UUID id, String name, Integer price) {
	
	public ProductResponseDTO(Product product){
        this(product.getId(), product.getName(), product.getPrice());
    }



	
}
