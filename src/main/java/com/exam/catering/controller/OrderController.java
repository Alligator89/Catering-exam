package com.exam.catering.controller;

import com.exam.catering.domain.Orders;
import com.exam.catering.exceptions.OrderNotFoundException;
import com.exam.catering.service.OrdersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrdersService ordersService;

    public OrderController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = ordersService.getOrders();
        if (!orders.isEmpty()) {
            log.info("List of orders is found!");
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            log.info("List of orders is not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrder(@PathVariable Integer id) {
        Orders order = ordersService.getOrder(id).orElseThrow(OrderNotFoundException::new);
        log.info("Order with id: " + id + " is found!");
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createOrder(@RequestBody Orders order) {
        ordersService.createOrder(order);
        log.info("Order with client`s id: " + order.getClient().getId() + " is created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateOrder(@RequestBody Orders order) {
        ordersService.updateOrder(order);
        log.info("Order with id: " + order.getId() + " is updated!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable Integer id) {
        ordersService.deleteOrderById(id);
        log.info("Order with id: " + id + " is deleted!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
