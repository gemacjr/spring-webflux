package com.example.demo.util;

import org.springframework.beans.BeanUtils;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;

public class ProductMapper {

	public static ProductDto toProductDto(Product product) {
		ProductDto dto = new ProductDto();
		BeanUtils.copyProperties(product, dto);
		return dto;
	}
	
	public static Product toProductEntity(ProductDto dto) {
		Product product = new Product();
		BeanUtils.copyProperties(dto, product);
		return product;
	}
}
