package com.shipmonk.testingday.persistence;

public class ExchangeRatesDataAccessException extends RuntimeException {

    public ExchangeRatesDataAccessException() {
        super("Failed to access exchange rates in DB");
    }
}
