package com.exam.catering.controller;

import com.exam.catering.domain.CreateMenuDTO;
import com.exam.catering.domain.OrderedMenu;
import com.exam.catering.domain.UpdateOrderedMenuDTO;
import com.exam.catering.exceptions.OrderedMenuNotFoundException;
import com.exam.catering.service.OrderedMenuService;
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

    @GetMapping("/{id}")
    public ResponseEntity<OrderedMenu> getOrderedMenu(@PathVariable Integer id) {
        OrderedMenu orderedMenu = orderedMenuService.getOrderedMenu(id).orElseThrow(OrderedMenuNotFoundException::new);
        log.info("OrderedMenu with id: " + id + " is found!");
        return new ResponseEntity<>(orderedMenu, HttpStatus.OK);
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<HttpStatus> createOrderedMenu(@RequestBody CreateMenuDTO createMenuDTO) {
        orderedMenuService.createOrderedMenu(createMenuDTO);
        log.info("OrderedMenu by orders`s id: " + createMenuDTO.getOrderId() + " is created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateOrderedMenu(@RequestBody UpdateOrderedMenuDTO updateOrderedMenuDTO) {
        orderedMenuService.updateOrderedMenu(updateOrderedMenuDTO);
        log.info("OrderedMenu with id: " + updateOrderedMenuDTO.getOrderedMenuId() + " is updated!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrderedMenu(@PathVariable Integer id) {
        orderedMenuService.deleteOrderedMenu(id);
        log.info("OrderedMenu with id: " + id + " is deleted!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
