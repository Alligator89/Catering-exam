package com.exam.catering.controller;

import com.exam.catering.domain.Dishes;
import com.exam.catering.exceptions.DishNotFoundException;
import com.exam.catering.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishesController {

    private final DishService dishService;

    public DishesController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Dishes>> getList() {
        List<Dishes> dishList = dishService.getList();
        if (dishList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(dishList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dishes> getDish(@PathVariable Integer id) {
        Dishes dish = dishService.getDish(id).orElseThrow(DishNotFoundException::new);
        return new ResponseEntity<>(dish, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createDish(@RequestBody Dishes dish) {
        dishService.createDish(dish);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateDish(@RequestBody Dishes dish) {
        dishService.updateDish(dish);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDish(@PathVariable Integer id) {
        dishService.deleteDish(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
