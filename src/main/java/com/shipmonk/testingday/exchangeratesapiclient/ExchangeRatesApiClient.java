package com.shipmonk.testingday.exchangeratesapiclient;

import java.time.LocalDate;

public interface ExchangeRatesApiClient {

    ExchangeRatesApiDto getRates(String baseCurrency, LocalDate date);
}
