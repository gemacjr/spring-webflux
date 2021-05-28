package com.example.webfluxdemovs.service;

import com.example.webfluxdemovs.dto.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MathService {
    public Response findSquare(int input){
        return new Response(input * input);
    }

    public List<Response> multiplicationTable(int input){
        return IntStream.rangeClosed(1, 10)
                .peek(i -> SleepUtil.sleepSeconds(1))
                .peek(i -> System.out.println("MathService processing : "+i))
                .mapToObj(i -> new Response(i * i))
                .collect(Collectors.toList());
    }
}
