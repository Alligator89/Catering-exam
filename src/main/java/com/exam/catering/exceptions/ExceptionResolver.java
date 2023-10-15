package com.exam.catering.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResolver {
    private static final Logger log = LoggerFactory.getLogger(ExceptionResolver.class);


    @ExceptionHandler(value = ClientNotFoundException.class)
    public ResponseEntity<HttpStatus> clientNotFoundException() {
        log.info("ClientNotFoundException exception!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OrderedMenuNotFoundException.class)
    public ResponseEntity<HttpStatus> orderedMenuNotFoundException() {
        log.info("OrderedMenuNotFoundException exception!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DishNotFoundException.class)
    public ResponseEntity<HttpStatus> dishNotFoundException() {
        log.info("DishNotFoundException exception!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MenuNotFoundException.class)
    public ResponseEntity<HttpStatus> menuNotFoundException() {
        log.info("MenuNotFoundException exception!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OrderNotFoundException.class)
    public ResponseEntity<HttpStatus> orderNotFoundException() {
        log.info("OrderNotFoundException exception!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ListOfOrdersNotFoundException.class)
    public ResponseEntity<HttpStatus> listOfOrdersNotFoundException() {
        log.info("ListOfOrdersNotFoundException exception!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<HttpStatus> illegalArgumentException() {
        log.info("IllegalArgumentException exception!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OptimisticLockingFailureException.class)
    public ResponseEntity<HttpStatus> optimisticLockingFailureException() {
        log.info("OptimisticLockingFailureException exception!");
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
