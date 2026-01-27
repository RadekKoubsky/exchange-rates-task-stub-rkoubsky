package com.shipmonk.testingday.service;

import com.shipmonk.testingday.exchangeratesapiclient.ExchangeRatesApiClient;
import com.shipmonk.testingday.exchangeratesapiclient.ExchangeRatesApiDto;
import com.shipmonk.testingday.persistence.ExchangeRatesPersistence;
import com.shipmonk.testingday.service.model.ExchangeRates;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRatesServiceTest {

    @Mock
    private ExchangeRatesApiClient exchangeRatesApiClient;

    @Mock
    private ExchangeRatesPersistence exchangeRatesPersistence;

    @InjectMocks
    private ExchangeRatesService exchangeRatesService;

    @Test
    void whenRatesInDB_thenDoNotCallApi() {
        var currency = "USD";
        var now = LocalDate.now();
        var uuid = UUID.randomUUID();
        var exchangeRates = new ExchangeRates(uuid, currency, now, Map.of("CZK", "24.243035", "DJF", "211.130875"));
        when(exchangeRatesPersistence.getRates(eq(currency), eq(now))).thenReturn(Optional.of(exchangeRates));

        exchangeRatesService.getRates(currency, now);

        verify(exchangeRatesApiClient, never()).getRates(Mockito.anyString(), Mockito.any());
        verify(exchangeRatesPersistence, never()).create(Mockito.any());
    }

    @Test
    void whenRatesNotInDB_thenFetchRatesFromApi() {
        var currency = "USD";
        var now = LocalDate.now();
        when(exchangeRatesPersistence.getRates(eq(currency), Mockito.any())).thenReturn(Optional.empty());
        when(exchangeRatesApiClient.getRates(eq(currency), Mockito.any())).thenReturn(
            new ExchangeRatesApiDto(
                true,
                null,
                currency,
                now,
                Map.of("CZK", "24.243035", "DJF", "211.130875")
            )
        );

        exchangeRatesService.getRates(currency, now);

        verify(exchangeRatesApiClient).getRates(currency, now);
        verify(exchangeRatesPersistence).create(Mockito.any());
    }
}
