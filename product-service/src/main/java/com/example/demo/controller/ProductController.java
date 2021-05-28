package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/all")
	public Flux<ProductDto> all(){
		return this.productService.getAll();
	}

	@GetMapping("price-range")
	public Flux<ProductDto> getByPriceRange(@RequestParam("min") int min,
			@RequestParam("max") int max){
		return this.productService.getProductByPriceRange(min, max);
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable String id){
		return this.productService.getProductById(id)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> productDtoMono){
		return this.productService.insertProduct(productDtoMono);
	}

	@PutMapping
	public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDto> productDtoMono) {
		return this.productService.updateProduct(id, productDtoMono)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping
	public Mono<Void> deleteProduct(String id) {
		return this.productService.deleteProduct(id);
	}
}
