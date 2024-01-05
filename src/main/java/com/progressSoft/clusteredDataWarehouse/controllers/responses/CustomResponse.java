package com.progressSoft.clusteredDataWarehouse.controllers.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomResponse <T> {
    private String message;
    private T data;
}
