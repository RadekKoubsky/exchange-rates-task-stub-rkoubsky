package com.shipmonk.testingday.rest;

public record RestErrorResponse(
    String status,
    Integer statusCode,
    RestError error
) {
}
