package com.progressSoft.clusteredDataWarehouse.service;

import com.progressSoft.clusteredDataWarehouse.models.dto.DealDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DealService {

    Long createDeal(DealDTO deal);

    DealDTO findDealById(Long id);

    Page<DealDTO> findAllDeals(Pageable pageable);
}
