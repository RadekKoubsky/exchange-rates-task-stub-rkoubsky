package com.shipmonk.testingday.exchangeratesapiclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ExchangeRatesApiClientImpl implements ExchangeRatesApiClient {
    private final Logger logger = LoggerFactory.getLogger(ExchangeRatesApiClientImpl.class);

    private final OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .build();

    private final ExchangeRatesClientProperties properties;
    private final ObjectMapper objectMapper;

    @Override
    @Cacheable(cacheNames = "exchangeRates")
    public ExchangeRatesApiDto getRates(String baseCurrency, LocalDate date) {
        /*
        * TODO Add retry logic if fixer service does not respond.
        *
        * E.g. using resilience4j library
        *
        * */
        logger.info("Fetching exchange rates from fixer for {} on {}", baseCurrency, date);
        try {
            Request request = getRequest(baseCurrency, date);
            try (Response response = client.newCall(request).execute()) {
                var exchangeRatesDto = objectMapper.readValue(response.body().byteStream(), ExchangeRatesApiDto.class);

                if (Boolean.FALSE.equals(exchangeRatesDto.success())) {
                    logger.error("Failed to fetch exchange rates: {}", exchangeRatesDto.error());
                    throw new ExchangeRatesClientException(exchangeRatesDto.error());
                }

                return exchangeRatesDto;
            }
        } catch (IOException exception) {
            logger.error("Failed to fetch exchange rates", exception);
            throw new ExchangeRatesClientException();
        }
    }

    private HttpUrl.Builder getBaseUrl() {
        var builder = new HttpUrl.Builder()
            .scheme(properties.scheme())
            .host(properties.host())
            .addPathSegment("api")
            .addQueryParameter("access_key", properties.accessKey());
        if (properties.port() != null) {
            builder.port(properties.port());
        }
        return builder;
    }

    private Request getRequest(String baseCurrency, LocalDate date) {
        HttpUrl url = getBaseUrl()
            .addPathSegment(date.toString())
            .addQueryParameter("base", baseCurrency)
            .build();

        return new Request.Builder()
            .url(url)
            .header("Accept", "application/json")
            .build();
    }
}
