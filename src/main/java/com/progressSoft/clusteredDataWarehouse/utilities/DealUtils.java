package com.progressSoft.clusteredDataWarehouse.utilities;

import com.progressSoft.clusteredDataWarehouse.models.dto.DealDTO;
import com.progressSoft.clusteredDataWarehouse.models.entity.Deal;
import lombok.extern.log4j.Log4j2;

import java.util.Currency;

@Log4j2
public class DealUtils {
    public static Deal convertDealDtoToDeal(DealDTO deal){
        log.debug("Map DTO to entity: Deal");
        return Deal.builder().
                dealId(deal.getDealId()).
                dealAmount(deal.getDealAmount()).
                toCurrencyIsoCode(Currency.getInstance(deal.getToCurrencyIsoCode())).
                fromCurrencyIsoCode(Currency.getInstance(deal.getFromCurrencyIsoCode())).
                build();
    }

    public static DealDTO convertDealToDTO(Deal deal){
        log.debug("Map DTO to entity: Deal");
        return DealDTO.builder().
                dealId(deal.getDealId()).
                dealAmount(deal.getDealAmount()).
                toCurrencyIsoCode(deal.getToCurrencyIsoCode().toString()).
                fromCurrencyIsoCode(deal.getFromCurrencyIsoCode().toString()).
                dealTimeStamp(deal.getDealTimeStamp()).
                build();
    }
}
