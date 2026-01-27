package com.shipmonk.testingday.exchangeratesapiclient;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchangerates.api.client")
public record ExchangeRatesClientProperties(
    String accessKey,
    String scheme,
    String host,
    Integer port
) {}
