package com.exam.catering.controller;

import com.exam.catering.domain.CreateMenuDTO;
import com.exam.catering.domain.OrderedMenu;
import com.exam.catering.domain.UpdateOrderedMenuDTO;
import com.exam.catering.exceptions.OrderedMenuNotFoundException;
import com.exam.catering.service.OrderedMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderedMenu")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderedMenuController {
    private static final Logger log = LoggerFactory.getLogger(OrderedMenuController.class);

    private final OrderedMenuService orderedMenuService;

    public OrderedMenuController(OrderedMenuService orderedMenuService) {
        this.orderedMenuService = orderedMenuService;
    }

    @Operation(summary = "Get orderedMenu", description = "Get one orderedMenu , need to pass the input parameter orderedMenu`s id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OrderedMenu is found"),
            @ApiResponse(responseCode = "404", description = "OrderedMenu is not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/{id}")
    public ResponseEntity<OrderedMenu> getOrderedMenu(@PathVariable Integer id) {
        OrderedMenu orderedMenu = orderedMenuService.getOrderedMenu(id).orElseThrow(OrderedMenuNotFoundException::new);
        log.info("OrderedMenu with id: " + id + " is found!");
        return new ResponseEntity<>(orderedMenu, HttpStatus.OK);
    }

    @Operation(summary = "Creating orderedMenu", description = "Create orderedMenu,  need to pass object CreateMenuDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OrderedMenu is created"),
            @ApiResponse(responseCode = "409", description = "OrderedMenu is not created"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PostMapping("/{orderId}")
    public ResponseEntity<HttpStatus> createOrderedMenu(@RequestBody CreateMenuDTO createMenuDTO) {
        orderedMenuService.createOrderedMenu(createMenuDTO);
        log.info("OrderedMenu by orders`s id: " + createMenuDTO.getOrderId() + " is created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Updating orderedMenu", description = "Update orderedMenu, need to pass object UpdateOrderedMenuDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OrderedMenu is updates"),
            @ApiResponse(responseCode = "409", description = "OrderedMenu is not updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PutMapping
    public ResponseEntity<HttpStatus> updateOrderedMenu(@RequestBody UpdateOrderedMenuDTO updateOrderedMenuDTO) {
        orderedMenuService.updateOrderedMenu(updateOrderedMenuDTO);
        log.info("OrderedMenu with id: " + updateOrderedMenuDTO.getOrderedMenuId() + " is updated!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Deleting orderedMenu", description = "Delete orderedMenu,  need to pass the input parameter orderedMenu`s id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OrderedMenu is deleted"),
            @ApiResponse(responseCode = "409", description = "OrderedMenu is not deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrderedMenu(@PathVariable Integer id) {
        orderedMenuService.deleteOrderedMenu(id);
        log.info("OrderedMenu with id: " + id + " is deleted!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
