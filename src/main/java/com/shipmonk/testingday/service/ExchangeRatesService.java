package com.shipmonk.testingday.service;

import com.shipmonk.testingday.persistence.ExchangeRatesPersistence;
import com.shipmonk.testingday.service.model.ExchangeRates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ExchangeRatesService {

    private final ExchangeRatesPersistence exchangeRatesPersistence;

    public ExchangeRates getRates(String baseCurrency, LocalDate date) {
        return exchangeRatesPersistence.getRates(baseCurrency, date).orElseThrow(() -> new RuntimeException("Exchange rates not found"));
    }

    public void create(ExchangeRates exchangeRates) {
        exchangeRatesPersistence.create(exchangeRates);
    }
}
