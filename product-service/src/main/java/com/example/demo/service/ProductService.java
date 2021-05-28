package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDto;
import com.example.demo.repository.ProductRepository;
import com.example.demo.util.ProductMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Flux<ProductDto> getAll(){
		return this.productRepository.findAll()
				.map(ProductMapper::toProductDto);
	}

	public Mono<ProductDto> getProductById(String id){
		return this.productRepository.findById(id)
				.map(ProductMapper::toProductDto);
	}


	public Mono<ProductDto> insertProduct( Mono<ProductDto> productDtoMono){
		return productDtoMono
				.map(ProductMapper::toProductEntity)
				.flatMap(this.productRepository::insert)
				.map(ProductMapper::toProductDto);
	}

	public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono){
		return this.productRepository.findById(id)
				.flatMap(p -> productDtoMono.map(ProductMapper::toProductEntity).doOnNext(e -> e.setId(id)))
				.flatMap(this.productRepository::save)
				.map(ProductMapper::toProductDto);
	}

	public Mono<Void> deleteProduct(String id) {
		return this.productRepository.deleteById(id);
	}

	public Flux<ProductDto> getProductByPriceRange(int min, int max){
        return this.productRepository.findByPriceBetween(Range.closed(min, max))
                .map(ProductMapper::toProductDto);
    }

}
