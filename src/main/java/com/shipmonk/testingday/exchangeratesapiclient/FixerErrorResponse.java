package com.shipmonk.testingday.exchangeratesapiclient;

public record FixerErrorResponse(
    boolean success,
    FixerError error
) {}

