package io.kokilaw.rupiyal.service;

import io.kokilaw.rupiyal.dto.ExchangeRateDTO;
import io.kokilaw.rupiyal.dto.ExchangeRateType;
import io.kokilaw.rupiyal.dto.DateExchangeRatesSummaryDTO;
import io.kokilaw.rupiyal.dto.ExtendedRateEntryDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by kokilaw on 2023-06-13
 */
public interface ExchangeRateService {

    void saveCurrencyRates(ExchangeRateType exchangeRateType, List<ExchangeRateDTO> currencyRates);

    DateExchangeRatesSummaryDTO getCurrencyRatesForTheDate(LocalDate date);
    DateExchangeRatesSummaryDTO getLatestCurrencyRates();

    Map<String, List<ExtendedRateEntryDTO>> getBuyingRates(String currencyCode, int lastNumberOfDays);

    Map<String, List<ExtendedRateEntryDTO>> getSellingRates(String currencyCode, int lastNumberOfDays);
}
