package com.theoctavegroup.jukeboxsettings.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Controller Advice to Handle Exceptions
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle WebClient exception
     */
    @ExceptionHandler(WebClientCustomException.class)
    public ResponseEntity<Object> handleWebClientStatusException(WebClientCustomException exception, WebRequest request)  {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, exception.getStatus());
    }

    // Handle specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle global exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalExceptionHandling(Exception exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
