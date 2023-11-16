package za.co.discovery.assignment.controller.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import za.co.discovery.assignment.dto.ErrorDto;
import za.co.discovery.assignment.exception.RouteNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorResponseAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Set<ErrorDto>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Set<ErrorDto> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> new ErrorDto(f.getObjectName(), f.getField(), f.getDefaultMessage()))
                .collect(Collectors.toSet());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RouteNotFoundException.class)
    protected ResponseEntity<ErrorDto> handleNotFoundException(RouteNotFoundException ex) {
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), HttpStatus.NOT_FOUND);
    }


}
