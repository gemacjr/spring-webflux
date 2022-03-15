package com.example.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }


    @Bean
    public RouterFunction<ServerResponse> routes(ReservationRepository rr) {
        return route()
                .GET("/reservations", request -> ServerResponse.ok().body(rr.findAll(), Reservation.class))
                .build();
    }
}

@Component
class SampleDataInit {
    @Autowired
    private ReservationRepository reservationRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void go() {
        Flux<Reservation> reservationFlux = Flux.just("Neha", "Aravind", "Laxmi", "Rajesh", "Anosh", "Sam", "Alex")
                .map(name -> Reservation.builder().name(name).build())
                .flatMap(r -> this.reservationRepository.save(r));

        this.reservationRepository.deleteAll()
                .thenMany(reservationFlux)
                .thenMany(this.reservationRepository.findAll())
                .subscribe(System.out::println);
    }
}

interface ReservationRepository extends ReactiveCrudRepository<Reservation, Integer> {

}


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
class Reservation {
    @Id
    private Integer id;
    private String name;
}

@Controller
class GreetingService {

    @MessageMapping("greetings")
    Flux<GreetingResponse> greet(GreetingRequest request) {
        return Flux.fromStream(Stream.generate(() ->
                        new GreetingResponse("Hello " + request.getName() + " @ " + Instant.now().toString())))
                .delayElements(Duration.ofSeconds(1));
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