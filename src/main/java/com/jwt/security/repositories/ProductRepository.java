package com.jwt.security.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.security.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
