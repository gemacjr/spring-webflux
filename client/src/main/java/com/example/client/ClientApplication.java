package com.example.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;

@Log4j2
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ClientApplication.class, args);
        System.in.read();
    }

    @Bean
    WebClient http(WebClient.Builder builder) {
        return builder.build();
    }

    @Bean
    RSocketRequester rSocket(RSocketRequester.Builder builder) {
        return builder.tcp("localhost", 7070);
    }

    @Bean
    ApplicationRunner httpClient(WebClient http) {
        return args -> {
            http
                    .get()
                    .uri("http://localhost:8080/reservations")
                    .retrieve()
                    .bodyToFlux(Reservation.class)
                    .map(r -> r.getName())
                    .onErrorResume(ex -> Flux.just("EEKK!"))
                    .retry()
                    .timeout(Duration.ofSeconds(1))
                    .subscribe(log::info);
        };
    }

    @Bean
    public ApplicationRunner rSocketClient(RSocketRequester rSocketRequester) {
        return args -> {
            rSocketRequester.route("greetings")
                    .data(new GreetingRequest("All the Talks"))
                    .retrieveFlux(GreetingResponse.class)
                    .subscribe(log::info);
        };
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class GreetingRequest {
    private String name;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class GreetingResponse {
    private String message;
}

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
class Reservation {
    private Integer id;
    private String name;
}

