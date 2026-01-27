package com.shipmonk.testingday.exchangeratesapiclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ExchangeRatesApiClientProviderImpl implements ExchangeRatesApiClientProvider {

    private final OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .build();

    private final ObjectMapper objectMapper;
    private final ExchangeRatesClientProperties properties;

    @Override
    public ExchangeRatesApiClient getClient() {
        return new ExchangeRatesApiClientImpl(client, properties, objectMapper);
    }
}
