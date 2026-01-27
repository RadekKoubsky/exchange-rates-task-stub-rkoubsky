package com.shipmonk.testingday.rest;

import java.time.OffsetDateTime;

public record RestError(
    String message,
    OffsetDateTime timestamp,
    String path
) {

}
