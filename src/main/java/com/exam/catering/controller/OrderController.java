package com.exam.catering.controller;

import com.exam.catering.domain.Orders;
import com.exam.catering.exceptions.OrderNotFoundException;
import com.exam.catering.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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

    @Operation(summary = "Get list of orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of orders is found"),
            @ApiResponse(responseCode = "404", description = "List of orders is not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
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

    @Operation(summary = "Get order", description = "Get one order , need to pass the input parameter order`s id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order is found"),
            @ApiResponse(responseCode = "404", description = "Order is not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrder(@PathVariable Integer id) {
        Orders order = ordersService.getOrder(id).orElseThrow(OrderNotFoundException::new);
        log.info("Order with id: " + id + " is found!");
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Operation(summary = "Creating order", description = "Create order,  need to pass object Orders in format JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order is created"),
            @ApiResponse(responseCode = "409", description = "Order is not created"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PostMapping
    public ResponseEntity<HttpStatus> createOrder(@Valid @RequestBody Orders order) {
        ordersService.createOrder(order);
        log.info("Order with client`s id: " + order.getClient().getId() + " is created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Payment order", description = "Payment order, need to pass order`s id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order is paid"),
            @ApiResponse(responseCode = "409", description = "Order is not paid"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PostMapping("/payment/{id}")
    public ResponseEntity<HttpStatus> paymentOrder(@PathVariable Integer id) {
        ordersService.paymentOrder(id);
        log.info("Order by id: " + id + " is paid!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Updating order", description = "Update order, need to pass object Orders in format JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order is updates"),
            @ApiResponse(responseCode = "409", description = "Order is not updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PutMapping
    public ResponseEntity<HttpStatus> updateOrder(@Valid @RequestBody Orders order) {
        ordersService.updateOrder(order);
        log.info("Order with id: " + order.getId() + " is updated!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Deleting order", description = "Delete order,  need to pass the input parameter order`s id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order is deleted"),
            @ApiResponse(responseCode = "409", description = "Order is not deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable Integer id) {
        ordersService.deleteOrderById(id);
        log.info("Order with id: " + id + " is deleted!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
