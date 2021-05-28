package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDto {
	private String id;
	private String desc;
	private Integer price;
	
	public ProductDto(String description, Integer price) {
        this.desc = description;
        this.price = price;
    }
}
