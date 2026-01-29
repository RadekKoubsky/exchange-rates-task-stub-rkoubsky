package com.shipmonk.testingday.exchangeratesapiclient;

public class ExchangeRatesClientException extends RuntimeException {

    public ExchangeRatesClientException() {
        super("Error occurred when fetching rates from the external Exchange Rates Service");
    }
}
