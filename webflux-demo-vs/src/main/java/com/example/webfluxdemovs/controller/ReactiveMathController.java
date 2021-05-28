package com.example.webfluxdemovs.controller;

import com.example.webfluxdemovs.dto.MultiplyRequestDto;
import com.example.webfluxdemovs.dto.Response;
import com.example.webfluxdemovs.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {
    @Autowired
    private ReactiveMathService mathService;

    @GetMapping("/square/{input}")
    public Mono<Response> findSquare(@PathVariable int input){
        return this.mathService.findSquare(input);
    }

    /**
     * If you cancel the request backend processing will immediately stop, but no output will appear on screen.
     * It works line Mono<List<Response>> it keep collecting values when flux completes, it returns value
     */
    @GetMapping("/table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input){
        return this.mathService.multiplicationTable(input);
    }

    /**
     * When you do not add "produces = MediaType.TEXT_EVENT_STREAM_VALUE", per AbstractJackson2Encoder
     * it collects everything and send it as single list
     */
    @GetMapping(value = "/table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input){
        return this.mathService.multiplicationTable(input);
    }

    @PostMapping("/multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono,
                                   @RequestHeader Map<String, String> headers){
        System.out.println("Headers = "+ headers);
        return this.mathService.multiply(requestDtoMono);
    }
}
