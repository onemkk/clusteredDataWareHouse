package com.progressSoft.clusteredDataWarehouse.exceptionHandling.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorMessage {
   private String statusCode;
    private String message;
    private final LocalDateTime time;
    private String path;
}
