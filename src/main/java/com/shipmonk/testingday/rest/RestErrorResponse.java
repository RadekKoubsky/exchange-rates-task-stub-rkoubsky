package com.shipmonk.testingday.rest;

public record RestErrorResponse(
    Integer statusCode,
    RestError error
) {
}
