package com.shipmonk.testingday.exchangeratesapiclient;

public record FixerError(
    int code,
    String type,
    String info
) {}
