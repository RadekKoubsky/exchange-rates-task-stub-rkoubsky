package com.shipmonk.testingday.persistence;

import com.shipmonk.testingday.service.model.ExchangeRates;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExchangeRatesPersistence {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRatesPersistence.class);

    private final ExchangeRatesRepository exchangeRatesRepository;
    private final ExchangeRatesEntityMapper mapper;

    public Optional<ExchangeRates> getRates(String baseCurrency, LocalDate date) {
        try {
            var exchangeRatesEntity =  exchangeRatesRepository.findByBaseCurrencyAndDate(baseCurrency, date);
            return exchangeRatesEntity.map(rates -> mapper.mapEntity(rates));
        } catch (Exception e) {
            logger.error("Exchange rates DB error", e);
            throw new ExchangeRatesDataAccessException();
        }
    }

    public ExchangeRates create(ExchangeRates exchangeRates) {
        try {
            return mapper.mapEntity(exchangeRatesRepository.save(mapper.mapRates(exchangeRates)));
        } catch (Exception e) {
            logger.error("Exchange rates DB error", e);
            throw new ExchangeRatesDataAccessException();
        }
    }
}
