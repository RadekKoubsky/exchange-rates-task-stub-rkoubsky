package com.shipmonk.testingday.persistence;

import com.shipmonk.testingday.service.model.ExchangeRates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExchangeRatesPersistence {

    private final ExchangeRatesRepository exchangeRatesRepository;
    private final ExchangeRatesEntityMapper mapper;

    public Optional<ExchangeRates> getRates(String baseCurrency, LocalDate date) {
        var exchangeRatesEntity =  exchangeRatesRepository.findByBaseCurrencyAndDate(baseCurrency, date);
        return exchangeRatesEntity.map(rates -> mapper.mapEntity(rates));
    }

    public void create(ExchangeRates exchangeRates) {
        exchangeRatesRepository.save(mapper.mapRates(exchangeRates));
    }
}
