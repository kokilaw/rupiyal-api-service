package io.kokilaw.rupiyal.common.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by kokilaw on 2023-06-13
 */
public record CurrencyRateDTO (LocalDate date, BigDecimal rate, String currencyCode, String bankCode) {}