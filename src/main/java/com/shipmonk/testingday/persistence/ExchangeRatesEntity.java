package com.shipmonk.testingday.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "exchange_rates")
@Getter
@Setter
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
    private Map<String, String> rates;
}
