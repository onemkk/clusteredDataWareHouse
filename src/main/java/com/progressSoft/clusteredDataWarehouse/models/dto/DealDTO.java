package com.progressSoft.clusteredDataWarehouse.models.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.progressSoft.clusteredDataWarehouse.annotation.IsCurrency;
import com.progressSoft.clusteredDataWarehouse.utilities.CustomNumDeserializer;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealDTO {
    @Positive
    @JsonDeserialize(using = CustomNumDeserializer.class)
    @NotNull(message = "The dealId cannot be empty/null or an invalid number (check dealId value to see if it contains only valid numbers)")
    private Long dealId;

    @IsCurrency
    private String fromCurrencyIsoCode;

    @IsCurrency
    private String toCurrencyIsoCode;

    @Positive
    @JsonDeserialize(using = CustomNumDeserializer.class)
    @NotNull(message = "The dealAmount cannot be empty/null or an invalid number (check dealAmount value to see if it contains only valid numbers)")
    private BigDecimal dealAmount;

    private Instant dealTimeStamp;
}
