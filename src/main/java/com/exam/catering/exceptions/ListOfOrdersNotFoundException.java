package com.exam.catering.exceptions;

public class ListOfOrdersNotFoundException extends RuntimeException {
    public ListOfOrdersNotFoundException() {
        super("List of orders is not found");
    }
}
