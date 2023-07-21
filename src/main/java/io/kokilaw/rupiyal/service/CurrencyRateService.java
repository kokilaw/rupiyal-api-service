package io.kokilaw.rupiyal.service;

import io.kokilaw.rupiyal.dto.CurrencyRateDTO;
import io.kokilaw.rupiyal.dto.CurrencyRateType;
import io.kokilaw.rupiyal.dto.DateCurrencyRatesDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by kokilaw on 2023-06-13
 */
public interface CurrencyRateService {

    void saveCurrencyRates(CurrencyRateType currencyRateType, List<CurrencyRateDTO> currencyRates);

    DateCurrencyRatesDTO getCurrencyRatesForTheDate(LocalDate date);

}
