package com.shipmonk.testingday.rest;

import com.shipmonk.testingday.service.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(
    path = "/api/v1/rates"
)
@RequiredArgsConstructor
public class ExchangeRatesController {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRatesController.class.getName());

    private final ExchangeRatesService exchangeRatesService;

    @RequestMapping(method = RequestMethod.GET, path = "/{day}")
    public ResponseEntity<Object> getRates(@PathVariable("day") String day)
    {
        logger.info("Received request for rates for day: {}", day);
        return new ResponseEntity<>(
            exchangeRatesService.getRates("USD", LocalDate.parse(day)),
            HttpStatus.OK
        );
    }

}
