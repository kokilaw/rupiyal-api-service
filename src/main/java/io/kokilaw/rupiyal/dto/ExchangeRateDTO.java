package io.kokilaw.rupiyal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by kokilaw on 2023-06-13
 */
public record ExchangeRateDTO(LocalDate date, BigDecimal rate, String currencyCode, String bankCode, LocalDateTime createdAt, LocalDateTime updatedAt) {
}