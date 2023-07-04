package io.kokilaw.rupiyal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by kokilaw on 2023-06-13
 */
public record BankDTO(LocalDate date, BigDecimal rate, String currencyCode, String bankCode) {}