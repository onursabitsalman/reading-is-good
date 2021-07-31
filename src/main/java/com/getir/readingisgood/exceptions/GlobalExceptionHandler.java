package com.getir.readingisgood.exceptions;

import com.getir.readingisgood.model.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex) {
        log.error(ex.getMessage());
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.NOT_FOUND.value());
        response.setErrorMessage(ex.getMessage());
        response.setErrorReason(HttpStatus.NOT_FOUND.getReasonPhrase());
        response.setErrorCreateTime(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> resourceAlreadyExists(ResourceAlreadyExistsException ex) {
        log.error(ex.getMessage());
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.CONFLICT.value());
        response.setErrorMessage(ex.getMessage());
        response.setErrorReason(HttpStatus.CONFLICT.getReasonPhrase());
        response.setErrorCreateTime(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorizedException(UnauthorizedException ex) {
        log.error(ex.getMessage());
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.UNAUTHORIZED.value());
        response.setErrorMessage(ex.getMessage());
        response.setErrorReason(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.setErrorCreateTime(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({CustomException.class, ConstraintViolationException.class})
    public ResponseEntity<ExceptionResponse> customException(Exception ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.BAD_REQUEST.value());
        response.setErrorMessage(ex.getMessage());
        response.setErrorReason(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setErrorCreateTime(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
