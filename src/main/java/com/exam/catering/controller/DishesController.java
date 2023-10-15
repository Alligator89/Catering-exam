package com.exam.catering.controller;

import com.exam.catering.domain.Dishes;
import com.exam.catering.exceptions.DishNotFoundException;
import com.exam.catering.service.DishService;
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

    @GetMapping("/{id}")
    public ResponseEntity<Dishes> getDish(@PathVariable Integer id) {
        Dishes dish = dishService.getDish(id).orElseThrow(DishNotFoundException::new);
        log.info("Dish with id: " + id + " is found!");
        return new ResponseEntity<>(dish, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createDish(@RequestBody Dishes dish) {
        dishService.createDish(dish);
        log.info("Dish with name: " + dish.getName() + " is created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateDish(@RequestBody Dishes dish) {
        dishService.updateDish(dish);
        log.info("Dish with id: " + dish.getId() + " is updated!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDish(@PathVariable Integer id) {
        dishService.deleteDish(id);
        log.info("Dish with id: " + id + " is deleted!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
