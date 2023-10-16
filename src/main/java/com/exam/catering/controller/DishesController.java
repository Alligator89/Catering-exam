package com.exam.catering.controller;

import com.exam.catering.domain.Dishes;
import com.exam.catering.exceptions.DishNotFoundException;
import com.exam.catering.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishesController {
    private static final Logger log = LoggerFactory.getLogger(DishesController.class);

    private final DishService dishService;

    public DishesController(DishService dishService) {
        this.dishService = dishService;
    }

    @Operation(summary = "Get list of dishes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of dishes is found"),
            @ApiResponse(responseCode = "404", description = "List of dishes is not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/list")
    public ResponseEntity<List<Dishes>> getList() {
        List<Dishes> dishList = dishService.getList();
        if (dishList.isEmpty()) {
            log.info("List of dishes is not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("List of dishes is found!");
            return new ResponseEntity<>(dishList, HttpStatus.OK);
        }
    }

    @Operation(summary = "Get dish", description = "Get one dish , need to pass the input parameter dish`s id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish is found"),
            @ApiResponse(responseCode = "404", description = "Dish is not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/{id}")
    public ResponseEntity<Dishes> getDish(@PathVariable Integer id) {
        Dishes dish = dishService.getDish(id).orElseThrow(DishNotFoundException::new);
        log.info("Dish with id: " + id + " is found!");
        return new ResponseEntity<>(dish, HttpStatus.OK);
    }

    @Operation(summary = "Creating dish", description = "Create dish,  need to pass object Dish in format JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish is created"),
            @ApiResponse(responseCode = "409", description = "Dish is not created"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PostMapping
    public ResponseEntity<HttpStatus> createDish(@Valid @RequestBody Dishes dish) {
        dishService.createDish(dish);
        log.info("Dish with name: " + dish.getName() + " is created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Updating dish", description = "Update dish, need to pass object Dish in format JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dish is updates"),
            @ApiResponse(responseCode = "409", description = "Dish is not updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PutMapping
    public ResponseEntity<HttpStatus> updateDish(@Valid @RequestBody Dishes dish) {
        dishService.updateDish(dish);
        log.info("Dish with id: " + dish.getId() + " is updated!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Deleting dish", description = "Delete dish,  need to pass the input parameter dish`s id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dish is deleted"),
            @ApiResponse(responseCode = "409", description = "Dish is not deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDish(@PathVariable Integer id) {
        dishService.deleteDish(id);
        log.info("Dish with id: " + id + " is deleted!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
