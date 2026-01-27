package com.shipmonk.testingday.rest;

import com.shipmonk.testingday.service.model.ExchangeRates;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRatesRestMapper {

    public ExchangesRatesDto map(ExchangeRates exchangeRates) {
        return new ExchangesRatesDto(exchangeRates.baseCurrency(), exchangeRates.date(), exchangeRates.rates());
    }
}
