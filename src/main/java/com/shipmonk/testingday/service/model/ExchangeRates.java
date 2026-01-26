package com.shipmonk.testingday.service.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public record ExchangeRates(
    UUID id,
    String baseCurrency,
    LocalDate date,
    Map<String, BigDecimal> rates
) {
}
