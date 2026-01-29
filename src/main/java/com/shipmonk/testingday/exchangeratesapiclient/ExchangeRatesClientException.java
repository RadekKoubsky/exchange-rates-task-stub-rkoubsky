package com.shipmonk.testingday.exchangeratesapiclient;

import lombok.Getter;

@Getter
public class ExchangeRatesClientException extends RuntimeException {
    private final ApiError error;

    public ExchangeRatesClientException() {
        super("Error occurred when fetching exchange rates");
        this.error = null;
    }

    public ExchangeRatesClientException(ApiError error) {
        super("Error occurred when fetching exchange rates");
        this.error = error;
    }
}
