/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shopap.order;

/**
 *
 * @author Mateusz
 */

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService service;
    public OrderController(OrderService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<OrderDetailsResponse> create(
            @Valid @RequestBody OrderCreateRequest request) {
        OrderDetailsResponse resp = service.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsResponse> getDetails(@PathVariable Long orderId) {
        OrderDetailsResponse resp = service.getOrderDetails(orderId);
        return ResponseEntity.ok(resp);
    }
}
