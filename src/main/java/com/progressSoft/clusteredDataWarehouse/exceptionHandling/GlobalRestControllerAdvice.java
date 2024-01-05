package com.progressSoft.clusteredDataWarehouse.exceptionHandling;

import com.progressSoft.clusteredDataWarehouse.exceptionHandling.errors.ErrorMessage;
import com.progressSoft.clusteredDataWarehouse.exceptionHandling.exception.InvalidInputException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class GlobalRestControllerAdvice {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex, WebRequest request){
        String errorMessage = ex.getMessage();
        ErrorMessage errorResponse = ErrorMessage.builder().statusCode(HttpStatus.NOT_IMPLEMENTED.name()).
                message(errorMessage).time(LocalDateTime.now()).
                path(getRequestUrl(request)).build();
        ex.printStackTrace();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpServerErrorException.NotImplemented.class, UnsupportedOperationException.class})
    public ResponseEntity<Object> handleNotImplemented(WebRequest request){
        ErrorMessage errorResponse = ErrorMessage.builder().statusCode(HttpStatus.NOT_IMPLEMENTED.name()).
                message("This endpoint has not been Implemented").time(LocalDateTime.now()).
                path(getRequestUrl(request)).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> handleAllExceptions(Exception ex, WebRequest request){
        ErrorMessage errorResponse = ErrorMessage.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.name()).
                message(ex.getMessage()).time(LocalDateTime.now()).
                path(getRequestUrl(request)).build();
        ex.printStackTrace();
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ex.printStackTrace();
        ErrorMessage errorResponse = ErrorMessage.builder().statusCode(HttpStatus.BAD_REQUEST.name()).
                message("not valid due to validation error: "+ ex.getMessage()).time(LocalDateTime.now()).
                path(getRequestUrl(request)).build();
        return new ResponseEntity<>( errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request) {
        ex.printStackTrace();
        ErrorMessage errorResponse = ErrorMessage.builder().statusCode(HttpStatus.BAD_REQUEST.name()).
                message("Resource does not exist: "+ ex.getMessage()).time(LocalDateTime.now()).
                path(getRequestUrl(request)).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ex.printStackTrace();
        return errors;
    }

    private String getRequestUrl(WebRequest request){
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}
