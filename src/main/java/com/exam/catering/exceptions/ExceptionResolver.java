package com.exam.catering.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResolver {


    @ExceptionHandler(value = ClientNotFoundException.class)
    public ResponseEntity<HttpStatus> clientNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OrderedMenuNotFoundException.class)
    public ResponseEntity<HttpStatus> orderedMenuNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DishNotFoundException.class)
    public ResponseEntity<HttpStatus> dishNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MenuNotFoundException.class)
    public ResponseEntity<HttpStatus> menuNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OrderNotFoundException.class)
    public ResponseEntity<HttpStatus> orderNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ListOfOrdersNotFoundException.class)
    public ResponseEntity<HttpStatus> listOfOrdersNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<HttpStatus> illegalArgumentException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OptimisticLockingFailureException.class)
    public ResponseEntity<HttpStatus> optimisticLockingFailureException() {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
