package com.shipmonk.testingday;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shipmonk.testingday.service.ExchangeRatesService;
import com.shipmonk.testingday.service.model.ExchangeRates;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainersConfiguration.class)
public class ExchangeRatesIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenGetRatesWithCorrectDate_thenReturnRates() throws Exception {
        Map<String, String> rates = Map.of(
            "CZK", "24.243035",
            "DJF", "211.130875",
            "DKK", "7.468154",
            "DOP", "74.812701"
        );
        exchangeRatesService.create(new ExchangeRates(
            null, "USD", LocalDate.parse("2026-01-26"), rates)
        );

        ResponseEntity<String> response = restTemplate
            .getForEntity("/api/v1/rates/2026-01-26", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        var body = response.getBody();
        Assertions.assertThat(body).contains("24.24");

    }
}
