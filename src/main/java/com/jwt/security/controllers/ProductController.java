package com.jwt.security.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.security.domain.product.Product;
import com.jwt.security.domain.product.ProductRequestDTO;
import com.jwt.security.domain.product.ProductResponseDTO;
import com.jwt.security.repositories.ProductRepository;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("product")
public class ProductController {
    private final ProductRepository repository;
	
	@Autowired
    public ProductController(ProductRepository repository) {
		this.repository = repository;
	}
	
	@PostMapping
    public ResponseEntity postProduct(@RequestBody @Valid ProductRequestDTO body){
        Product newProduct = new Product(body);

        this.repository.save(newProduct);
        return ResponseEntity.ok().build();
    }
	
	 @GetMapping
	 public ResponseEntity getAllProducts(){
	      List<ProductResponseDTO> productList = this.repository.findAll().stream().map(ProductResponseDTO::new).toList();

	     return ResponseEntity.ok(productList);
	}
}
