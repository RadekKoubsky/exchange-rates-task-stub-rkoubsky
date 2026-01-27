package com.shipmonk.testingday.exchangeratesapiclient;

public record ApiError(
    int code,
    String type,
    String info
) {}
