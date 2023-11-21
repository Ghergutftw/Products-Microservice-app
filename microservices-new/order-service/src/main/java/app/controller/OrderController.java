package app.controller;

import app.dto.OrderRequest;
import app.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    public String createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order is placed successfully !";
    }
//    Needs to have the same signature as the method it is replacing
    public String fallbackMethod(OrderRequest orderRequest , RuntimeException e){
        return "Order Service is taking too long to respond or is down. Please try again later";
    }
}
