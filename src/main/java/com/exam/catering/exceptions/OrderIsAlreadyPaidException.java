package com.exam.catering.exceptions;

public class OrderIsAlreadyPaidException extends RuntimeException {
    public OrderIsAlreadyPaidException() {
        super("Order is already paid");
    }
}
