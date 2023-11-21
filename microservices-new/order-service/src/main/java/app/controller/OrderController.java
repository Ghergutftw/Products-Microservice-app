package app.controller;

import app.dto.OrderRequest;
import app.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    public CompletableFuture<String> createOrder(@RequestBody OrderRequest orderRequest) {
//        All of this changing stuff is to make the method asynchronous
       return CompletableFuture.supplyAsync(() ->orderService.placeOrder(orderRequest));
    }
//    Needs to have the same signature as the method it is replacing
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest , RuntimeException e){
        return CompletableFuture.supplyAsync(() ->"Order Service is taking too long to respond or is down. Please try again later");
    }
}
