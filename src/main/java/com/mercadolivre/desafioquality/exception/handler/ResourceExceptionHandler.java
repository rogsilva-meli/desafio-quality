package com.mercadolivre.desafioquality.exception.handler;

import com.mercadolivre.desafioquality.exception.error.BadRequestException;
import com.mercadolivre.desafioquality.exception.error.NotFoundException;
import com.mercadolivre.desafioquality.exception.error.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> notFound(NotFoundException e){
        StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> badRequest(BadRequestException e){
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validateField(MethodArgumentNotValidException e){
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                e.getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> validateConstructor(HttpMessageNotReadableException e){
        String msg = "It's necessary include { 'field: value }";
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                msg +" " + e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
