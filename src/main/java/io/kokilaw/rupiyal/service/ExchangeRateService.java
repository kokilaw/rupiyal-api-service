package io.kokilaw.rupiyal.service;

import io.kokilaw.rupiyal.dto.ExchangeRateDTO;
import io.kokilaw.rupiyal.dto.ExchangeRateType;
import io.kokilaw.rupiyal.dto.DateExchangeRatesSummaryDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by kokilaw on 2023-06-13
 */
public interface ExchangeRateService {

    void saveCurrencyRates(ExchangeRateType exchangeRateType, List<ExchangeRateDTO> currencyRates);

    DateExchangeRatesSummaryDTO getCurrencyRatesForTheDate(LocalDate date);

}
