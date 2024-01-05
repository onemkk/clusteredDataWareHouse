package com.progressSoft.clusteredDataWarehouse.controllers;

import com.progressSoft.clusteredDataWarehouse.controllers.responses.CustomResponse;
import com.progressSoft.clusteredDataWarehouse.controllers.responses.ImportDealResponse;
import com.progressSoft.clusteredDataWarehouse.models.dto.DealDTO;
import com.progressSoft.clusteredDataWarehouse.service.DealService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Validated
@RequestMapping("/warehouse/api/deals")
public class DealController {
    private final Logger log = LoggerFactory.getLogger(DealController.class);
    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @PostMapping()
    public ResponseEntity<CustomResponse<ImportDealResponse>>  createDeal(@Valid  @RequestBody DealDTO dealRequestBody){
        log.debug("Request to accept deal");
        Long dealId = dealService.createDeal(dealRequestBody);
        CustomResponse<ImportDealResponse> response = CustomResponse.<ImportDealResponse>builder().
                data(ImportDealResponse.builder().id(dealId).build()).
                message("Success").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CustomResponse<DealDTO>> getDealByUniqueId( @PathVariable("id") Long id) {
        log.debug("REST request to get Deal by : {}", id);
        DealDTO deal = dealService.findDealById(id);
        CustomResponse<DealDTO> response = CustomResponse.<DealDTO>builder().
                data(deal).
                message("Success").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<CustomResponse<Page<DealDTO>>> getAllFxDeals(@PageableDefault(value = 100) Pageable pageable) {
        log.debug("REST request to get list of fxDeals");
        Page<DealDTO> deals = dealService.findAllDeals(pageable);
        CustomResponse<Page<DealDTO>> response = CustomResponse.<Page<DealDTO>>builder().
                data(deals).
                message("Success").build();
        return ResponseEntity.ok(response);
    }


}
