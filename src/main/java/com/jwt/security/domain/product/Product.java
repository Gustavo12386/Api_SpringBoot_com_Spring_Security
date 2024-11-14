package com.jwt.security.domain.product;

import java.util.UUID;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "product")
@Entity(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Access(AccessType.FIELD)

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private UUID id;

    private String name;

    private Integer price;   
    
    public Product() {
    	
    }
    
    public Product(ProductRequestDTO data){    	
        this.price = data.price();
        this.name = data.name();
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}
	
}
