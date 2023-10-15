package com.exam.catering.exceptions;

public class OrderedMenuNotFoundException extends RuntimeException {
    public OrderedMenuNotFoundException() {
        super("OrderedMenu is not found");
    }
}
