package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebClientApi {

	private WebClient webClient;
	
	public WebClientApi() {
		this.webClient = WebClient.builder().baseUrl("http://localhost:8080/products").build();
	}
	
	private Mono<ResponseEntity<Product>> postNewProduct(){
		return webClient
				.post()
				.body(Mono.just(new Product(null, "Black Tea", 1.99)), Product.class)
				.exchange()
				.flatMap(response -> response.toEntity(Product.class))
				.doOnSubscribe(o -> System.out.println("************ POST" + o));
	}
	
	private Flux<Product> getAllProduct(){
		return webClient
				.get()
				.retrieve()
				.bodyToFlux(Product.class)
				.doOnNext(o -> System.out.println("********** GET" + o));
	}
	
	private Mono<Product> updateProduct(String id, String name, double price){
		return webClient
				.put()
				.uri("/{id}", id)
				.body(Mono.just(new Product(null, name, price)), Product.class)
				.retrieve()
				.bodyToMono(Product.class)
				.doOnSubscribe(o -> System.out.println("********** UPDATE" + o));
	}
	
	public Mono<Void> deleteProduct(String id){
		return webClient
				.delete()
				.uri("/{id}", id)
				.retrieve()
				.bodyToMono(Void.class)
				.doOnSuccess(o -> System.out.println("********** DELETE" + o));
	}
	
	public Flux<ProductEvent> getAllEvent(){
		return webClient
				.get()
				.uri("/events")
				.retrieve()
				.bodyToFlux(ProductEvent.class);
	}
	
	
	
	 public static void main(String[] args) {
		WebClientApi api = new WebClientApi();
		api.postNewProduct().thenMany(api.getAllProduct()).take(1)
			.flatMap(p -> api.updateProduct(p.getId(), "White Tea", 0.99))
			.flatMap(p -> api.deleteProduct(p.getId()))
			.thenMany(api.getAllProduct())
			.thenMany(api.getAllEvent()).subscribe(System.out::println);
	}
}
