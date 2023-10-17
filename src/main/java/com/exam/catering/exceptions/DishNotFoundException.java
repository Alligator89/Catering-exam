package com.exam.catering.exceptions;

public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException() {
        super("Dish is not found");
    }
}
