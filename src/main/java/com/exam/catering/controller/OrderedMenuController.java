package com.exam.catering.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderedMenu")
public class OrderedMenuController {

    private final OrderedMenuService orderedMenuService;

    public OrderedMenuController(OrderedMenuService orderedMenuService) {
        this.orderedMenuService = orderedMenuService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderedMenu> getOrderedMenu(@PathVariable Integer id) {
        OrderedMenu orderedMenu = orderedMenuService.getOrderedMenu(id).orElseThrow(OrderedMenuNotFoundException::new);
        return new ResponseEntity<>(orderedMenu, HttpStatus.OK);
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<HttpStatus> createOrderedMenu(@RequestBody CreateMenuDTO createMenuDTO) {
        orderedMenuService.createOrderedMenu(createMenuDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateOrderedMenu(@RequestBody UpdateOrderedMenuDTO updateOrderedMenuDTO) {
        orderedMenuService.updateOrderedMenu(updateOrderedMenuDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrderedMenu(@PathVariable Integer id) {
        orderedMenuService.deleteOrderedMenu(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
