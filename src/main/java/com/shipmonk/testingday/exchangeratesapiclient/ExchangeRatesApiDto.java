package com.shipmonk.testingday.exchangeratesapiclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExchangeRatesApiDto (
    Boolean success,
    ApiError error,
    String base,
    LocalDate date,
    Map<String, String> rates
) {
}
