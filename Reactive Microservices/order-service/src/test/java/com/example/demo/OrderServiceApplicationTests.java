package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.client.ProductClient;
import com.example.demo.client.UserClient;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.PurchaseOrderRequestDto;
import com.example.demo.dto.PurchaseOrderResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.OrderFulfillmentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {

	@Autowired
	private UserClient userClient;
	@Autowired
	private ProductClient productClient;
	@Autowired
	private OrderFulfillmentService fullfillmentService;
	
	
	
	@Test
	void contextLoads() {
		Flux<PurchaseOrderResponseDto> dtoFlux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
			.map(t -> buildDto(t.getT1(), t.getT2()))
			.flatMap(dto -> this.fullfillmentService.processOrder(Mono.just(dto)))
			.doOnNext(System.out::println);
		
		StepVerifier.create(dtoFlux).expectNextCount(4).verifyComplete();
	}
	
	private PurchaseOrderRequestDto buildDto(UserDto userDto, ProductDto productDto) {
		PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
		dto.setUserId(userDto.getId());
		dto.setProductId(productDto.getId());
		return dto;
	}

}
