package com.shipmonk.testingday;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.shipmonk.testingday.rest.ExchangesRatesDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

import java.net.URI;
import java.time.LocalDate;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
properties = {
    "exchangerates.api.client.access_key=test_key",
    "exchangerates.api.client.scheme=http",
    "exchangerates.api.client.host=localhost",
    "exchangerates.api.client.port=8086"})
@Import(TestContainersConfiguration.class)
@EnableWireMock({
    @ConfigureWireMock(name = "fixer-service", port = 8086),
})
public class ExchangeRatesIntegrationTest {

    @InjectWireMock("fixer-service")
    WireMockServer fixerServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenGetRatesWithCorrectDate_thenReturnRates() throws Exception {
        var currency = "USD";
        var now = LocalDate.now();
        Map<String, String> rates = Map.of(
            "CZK", "24.888888",
            "DJF", "211.130875",
            "DKK", "7.468154",
            "DOP", "74.812701"
        );

        fixerServiceMock.stubFor(get(String.format("/api/%s?access_key=test_key&base=%s", now, currency))
            .willReturn(okJson(objectMapper.writeValueAsString(Map.of(
                "success", true,
                "base", currency,
                "date", now.toString(),
                "rates", rates
            )))));

        URI uri = UriComponentsBuilder.fromPath("/api/v1/rates/{date}")
            .queryParam("baseCurrency", currency)
            .buildAndExpand(now)
            .toUri();

        ResponseEntity<ExchangesRatesDto> response = restTemplate
            .getForEntity(uri, ExchangesRatesDto.class);

        var exchangeRatesDto = response.getBody();
        Assertions.assertThat(exchangeRatesDto).isEqualTo(new ExchangesRatesDto(currency, now, rates));

    }
}
