package com.shipmonk.testingday.service;

import com.shipmonk.testingday.exchangeratesapiclient.ExchangeRatesApiClient;
import com.shipmonk.testingday.exchangeratesapiclient.ExchangeRatesApiDto;
import com.shipmonk.testingday.persistence.ExchangeRatesPersistence;
import com.shipmonk.testingday.service.model.ExchangeRates;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ExchangeRatesService {
    private final Logger logger = LoggerFactory.getLogger(ExchangeRatesService.class.getName());

    private final ExchangeRatesPersistence exchangeRatesPersistence;
    private final ExchangeRatesApiClient exchangeRatesApiClient;

    public ExchangeRates getRates(String baseCurrency, LocalDate date) {
        return exchangeRatesPersistence.getRates(baseCurrency, date).orElseGet(() -> {
            logger.info("Rates not found for {} on {}, fetching data from exchange rates api", baseCurrency, date);
            var ratesDto = exchangeRatesApiClient.getRates(baseCurrency, date);
            return create(mapRates(ratesDto));
        });
    }

    public ExchangeRates create(ExchangeRates exchangeRates) {
        return exchangeRatesPersistence.create(exchangeRates);
    }

    private ExchangeRates mapRates(ExchangeRatesApiDto ratesDto) {
        return new ExchangeRates(
            null,
            ratesDto.base(),
            ratesDto.date(),
            ratesDto.rates()
        );
    }
}
