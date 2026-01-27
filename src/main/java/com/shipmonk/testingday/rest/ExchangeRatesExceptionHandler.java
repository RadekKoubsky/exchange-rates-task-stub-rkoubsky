package com.shipmonk.testingday.rest;

import com.shipmonk.testingday.exchangeratesapiclient.ExchangeRatesClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ExchangeRatesExceptionHandler {


    @ExceptionHandler(ExchangeRatesClientException.class)
    public ResponseEntity<RestErrorResponse> handleApiClientException(ExchangeRatesClientException ex, WebRequest request) {
        String path = getPath(request);
        var error = new RestErrorResponse(
            "error",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            new RestError(
                "An unexpected error occurred",
                OffsetDateTime.now(),
                path
            )

        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        String path = getPath(request);
        var error = new RestErrorResponse(
            "error",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            new RestError(
                "An unexpected error occurred",
                OffsetDateTime.now(),
                path
            )

        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
