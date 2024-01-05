package com.progressSoft.clusteredDataWarehouse.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@Builder
@Entity
@Table(name = "deals")
@AllArgsConstructor
@NoArgsConstructor
public class Deal extends BaseEntity{
    @Id
    @Column(name = "deal_id", nullable = false, updatable = false, unique = true)
    private Long dealId;

    @Column(name = "from_currency_iso_code", nullable = false)
    private Currency fromCurrencyIsoCode;

    @Column(name = "to_country_iso_code", nullable = false)
    private Currency toCurrencyIsoCode;

    @Column(name = "deal_amount", nullable = false)
    private BigDecimal dealAmount;
}
