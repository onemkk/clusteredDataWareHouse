package com.progressSoft.clusteredDataWarehouse.service.impl;

import com.progressSoft.clusteredDataWarehouse.exceptionHandling.exception.InvalidInputException;
import com.progressSoft.clusteredDataWarehouse.models.dto.DealDTO;
import com.progressSoft.clusteredDataWarehouse.models.entity.Deal;
import com.progressSoft.clusteredDataWarehouse.repository.DealRepository;
import com.progressSoft.clusteredDataWarehouse.service.DealService;
import com.progressSoft.clusteredDataWarehouse.utilities.DealUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;

    public DealServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @Override
    public Long createDeal(DealDTO dealRequestBody) {
        Deal deal = dealRepository.save(validateDeal(dealRequestBody));
        return deal.getDealId();
    }

    public DealDTO findDealById(Long id){
        return dealRepository.findByDealId(id).map(DealUtils::convertDealToDTO).orElseThrow(()->{
            throw new InvalidInputException("Deal not found", "id");
        });
    }

    public Page<DealDTO> findAllDeals(Pageable pageable){
        return dealRepository.findAll(pageable).map(DealUtils::convertDealToDTO);
    }

    private Deal validateDeal(DealDTO dealRequestBody){
        log.debug("Validating deal in service ");
        if (Objects.isNull(dealRequestBody)){
            throw new InvalidInputException("Deal object cannot be null", "Deal");
        }
        Optional<Deal> deal = dealRepository.findByDealId(dealRequestBody.getDealId());
        if (deal.isPresent()){
            throw new InvalidInputException("Deal already exists ", "dealId");
        }
        return DealUtils.convertDealDtoToDeal(dealRequestBody);
    }


}
