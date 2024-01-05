package com.progressSoft.clusteredDataWarehouse.service.impl;

import com.progressSoft.clusteredDataWarehouse.exceptionHandling.exception.InvalidInputException;
import com.progressSoft.clusteredDataWarehouse.models.dto.DealDTO;
import com.progressSoft.clusteredDataWarehouse.models.entity.Deal;
import com.progressSoft.clusteredDataWarehouse.repository.DealRepository;
import com.progressSoft.clusteredDataWarehouse.service.DealService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = DealServiceImpl.class)
class DealServiceImplTest {

    @MockBean
    private DealRepository dealRepository;

    @Autowired
    private DealService dealService;

    @Test
    void createDeal() {
        DealDTO dealRequestBody = DealDTO.builder().dealId(31L).dealAmount(new BigDecimal(2000)).
                toCurrencyIsoCode("USD").fromCurrencyIsoCode("EUR").build();
        Deal deal = Deal.builder().dealId(31L).dealAmount(new BigDecimal(2000)).
                fromCurrencyIsoCode(Currency.getInstance("EUR")).
                toCurrencyIsoCode(Currency.getInstance("USD")).build();
        when(dealRepository.save(any(Deal.class))).thenReturn(deal);
        final Long dealId = dealService.createDeal(dealRequestBody);
        assertThat(dealId).isEqualTo(dealRequestBody.getDealId());
    }

    @Test
    void findDealById1() {
        when(dealRepository.findByDealId(anyLong())).thenReturn(Optional.empty());
         assertThatExceptionOfType(InvalidInputException.class).
                 isThrownBy(()-> dealService.findDealById(31L)).withMessageContaining("Deal not found");

    }

    @Test
    void findDealById2() {
        Deal deal = Deal.builder().dealId(31L).dealAmount(new BigDecimal(2000)).
                fromCurrencyIsoCode(Currency.getInstance("EUR")).
                toCurrencyIsoCode(Currency.getInstance("USD")).build();
        when(dealRepository.findByDealId(anyLong())).thenReturn(Optional.of(deal));
        DealDTO dealDTO = dealService.findDealById(31L);
        DealDTO expectedDealDto = DealDTO.builder().dealId(31L).dealAmount(new BigDecimal(2000)).
                toCurrencyIsoCode("USD").fromCurrencyIsoCode("EUR").build();
        assertThat(dealDTO).usingRecursiveComparison().ignoringFields("dealTimeStamp").isEqualTo(expectedDealDto);
    }

    @Test
    void findAllDeals() {
        when(dealRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());
        Page<DealDTO> pageOfDeals = dealService.findAllDeals(Pageable.ofSize(20));
        assertThat(pageOfDeals).isEmpty();

    }
}