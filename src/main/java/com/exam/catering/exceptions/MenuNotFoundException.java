package com.exam.catering.exceptions;

public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException() {
        super("Menu is not found");
    }
}
