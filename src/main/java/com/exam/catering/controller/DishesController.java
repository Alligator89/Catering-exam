package com.exam.catering.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dishes")
public class DishesController {

    private final DishService dishService;

    public DishesController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Dish>> getList() {
        List<Dish> dishList = dishService.getList();
        if (dishList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(dishList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDish(@PathVariable Integer id) {
        Dish dish = dishService.getDish(id).orElseThrow(DishNotFoundException::new);
        return new ResponseEntity<>(dish, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createDish(@RequestBody Dish dish) {
        dishService.createDish(dish);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateDish(@RequestBody Dish dish) {
        dishService.updateDish(dish);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDish(@PathVariable Integer id) {
        dishService.deleteDish(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
