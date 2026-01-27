package com.shipmonk.testingday.rest;

import com.shipmonk.testingday.service.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(
    path = "/api/v1/rates"
)
@RequiredArgsConstructor
public class ExchangeRatesController {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRatesController.class.getName());

    private final ExchangeRatesService exchangeRatesService;
    private final ExchangeRatesRestMapper mapper;

    @RequestMapping(method = RequestMethod.GET, path = "/{day}")
    public ResponseEntity<ExchangesRatesDto> getRates(@PathVariable("day") String day, @RequestParam String baseCurrency)
    {
        return new ResponseEntity<>(
            mapper.map(exchangeRatesService.getRates(baseCurrency, LocalDate.parse(day))),
            HttpStatus.OK
        );
    }

}
