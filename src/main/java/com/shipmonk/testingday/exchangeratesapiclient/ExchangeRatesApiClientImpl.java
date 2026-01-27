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
        logger.info("Fetching exchange rates from fixer for {} on {}", baseCurrency, date);
        try {
            HttpUrl url = getBaseUrl()
                .addPathSegment(date.toString())
                .addQueryParameter("base", baseCurrency)
                .build();

            Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new ExchangeRatesClientException("Unexpected code " + response);
                }

                var exchangeRatesDto = objectMapper.readValue(response.body().byteStream(), ExchangeRatesApiDto.class);

                if (Boolean.FALSE.equals(exchangeRatesDto.success())) {
                    logger.error("Failed to fetch exchange rates: {}", exchangeRatesDto.error());
                    throw new ExchangeRatesClientException("Failed to fetch exchange rates");
                }

                return exchangeRatesDto;
            }
        } catch (Exception exception) {
            logger.error("Failed to fetch exchange rates", exception);
            throw new ExchangeRatesClientException("Failed to fetch exchange rates");
        }
    }

    private HttpUrl.Builder getBaseUrl() {
        return new HttpUrl.Builder()
            .scheme(properties.scheme())
            .host(properties.host())
            .addPathSegment("api")
            .addQueryParameter("access_key", properties.accessKey());
    }
}

