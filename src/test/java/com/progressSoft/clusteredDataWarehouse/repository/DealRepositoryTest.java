package com.progressSoft.clusteredDataWarehouse.repository;

import com.progressSoft.clusteredDataWarehouse.models.entity.Deal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DealRepositoryTest {

    @Container
    @ServiceConnection
    private static final MySQLContainer<?> container = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));

    @Autowired
    private DealRepository dealRepository;

    @Test
    void findByDealId1() {
        final Optional<Deal> deal = dealRepository.findByDealId(21L);
        assertThat(deal).isEmpty();
    }

    @Test
    void findByDealId2() {
        final Deal deal = Deal.builder().dealId(21L).dealAmount(new BigDecimal(1000)).
                fromCurrencyIsoCode(Currency.getInstance("USD")).
                toCurrencyIsoCode(Currency.getInstance("EUR")).
                build();
        final Deal savedDeal = dealRepository.save(deal);
        final Optional<Deal> dealOp = dealRepository.findByDealId(21L);
        assertThat(dealOp).hasValue(savedDeal);
    }
}