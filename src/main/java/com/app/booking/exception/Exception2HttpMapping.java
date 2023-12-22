package com.app.booking.exception;

import com.app.booking.model.Errors;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class Exception2HttpMapping {

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Errors> handleMethodArgumentNotValidException(ConstraintViolationException ex, WebRequest request) {
        Errors exceptionWrapper = new Errors("Invalid Parameter(s)", ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(exceptionWrapper, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Errors> handleException(IllegalArgumentException ex, WebRequest request) {
        Errors exceptionWrapper = new Errors("Illegal Arguments", ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(exceptionWrapper, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Errors> handleException(MethodArgumentNotValidException ex, WebRequest request) {
        Errors exceptionWrapper = new Errors("Method Argument Not Valid", ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(exceptionWrapper, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Errors> handleException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        Errors exceptionWrapper = new Errors("Method not Allowed", ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(exceptionWrapper, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Errors> handleException(HttpMessageNotReadableException ex, WebRequest request) {
        Errors exceptionWrapper = new Errors("The method doesn't respect the validation", ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(exceptionWrapper, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingException.class)
    public final ResponseEntity<Errors> handleException(BookingException ex, WebRequest request) {
        Errors exceptionWrapper = new Errors("An error occurred for a booking", ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(exceptionWrapper, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BlockException.class)
    public final ResponseEntity<Errors> handleException(BlockException ex, WebRequest request) {
        Errors exceptionWrapper = new Errors("An error occurred for a block", ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(exceptionWrapper, headers, HttpStatus.BAD_REQUEST);
    }

}
