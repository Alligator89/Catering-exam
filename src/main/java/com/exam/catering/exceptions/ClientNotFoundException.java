package com.exam.catering.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException() {
        super("Client is not found");
    }
}
