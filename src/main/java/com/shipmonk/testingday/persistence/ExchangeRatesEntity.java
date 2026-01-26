package com.shipmonk.testingday.persistence;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRatesEntity {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @Column(name = "base_currency", nullable = false, length = 3)
    private String baseCurrency;

    @Column(nullable = false)
    private LocalDate date;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rates", nullable = false)
    private Map<String, BigDecimal> rates;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getBaseCurrency() { return baseCurrency; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Map<String, BigDecimal> getRates() { return rates; }
    public void setRates(Map<String, BigDecimal> rates) { this.rates = rates; }
}
