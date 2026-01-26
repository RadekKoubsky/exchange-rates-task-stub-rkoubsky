package com.shipmonk.testingday.persistence;

import com.shipmonk.testingday.service.model.ExchangeRates;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRatesEntityMapper {

    public ExchangeRates mapEntity(ExchangeRatesEntity exchangeRatesEntity) {
        return new ExchangeRates(
            exchangeRatesEntity.getId(),
            exchangeRatesEntity.getBaseCurrency(),
            exchangeRatesEntity.getDate(),
            exchangeRatesEntity.getRates()
        );
    }

    public ExchangeRatesEntity mapRates(ExchangeRates exchangeRates) {
        var entity = new ExchangeRatesEntity();
        entity.setBaseCurrency(exchangeRates.baseCurrency());
        entity.setDate(exchangeRates.date());
        entity.setRates(exchangeRates.rates());
        return entity;
    }
}
