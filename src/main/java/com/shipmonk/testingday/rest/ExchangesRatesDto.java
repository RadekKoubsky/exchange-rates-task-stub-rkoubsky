package com.shipmonk.testingday.rest;

import java.time.LocalDate;
import java.util.Map;

public record ExchangesRatesDto (
    String baseCurrency,
    LocalDate date,
    Map<String, String> rates
) {
}
