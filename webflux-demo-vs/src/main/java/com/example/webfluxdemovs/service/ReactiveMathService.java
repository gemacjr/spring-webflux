package com.example.webfluxdemovs.service;

import com.example.webfluxdemovs.dto.MultiplyRequestDto;
import com.example.webfluxdemovs.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input){
        return Mono.fromSupplier(() -> input * input)
                .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input){
        return Flux.range(1, 10)
                // This is unblocking way - When you cancel the request, its immediately cancel it
                .delayElements(Duration.ofSeconds(1))
                // This is blocking way - when you cancel the request still it will be processed till the end
                .doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> System.out.println("ReactiveMathService processing : "+ i))
                .map(i -> new Response(i * input));
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDto> requestDtoMono){
        return  requestDtoMono
                .map(dto -> dto.getFirst() * dto.getSecond())
                .map(Response::new);
    }
}
