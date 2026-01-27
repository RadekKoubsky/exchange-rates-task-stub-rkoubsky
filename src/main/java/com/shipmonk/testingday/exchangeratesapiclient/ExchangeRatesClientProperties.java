package com.shipmonk.testingday.exchangeratesapiclient;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchangeratesclient")
public record ExchangeRatesClientProperties(
    String accessKey,
    String scheme,
    String host
) {}
