package com.progressSoft.clusteredDataWarehouse.exceptionHandling.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidInputException extends RuntimeException{

    private String entityType;

    public InvalidInputException(String message, String entityType){
        super("Invalid Input ("+ entityType+"):"+ message);
    }
}
