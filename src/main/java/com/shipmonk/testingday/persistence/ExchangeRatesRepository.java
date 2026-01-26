package com.shipmonk.testingday.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExchangeRatesRepository extends JpaRepository<ExchangeRatesEntity, UUID> {

    Optional<ExchangeRatesEntity> findByBaseCurrencyAndDate(String baseCurrency, LocalDate date);
}
