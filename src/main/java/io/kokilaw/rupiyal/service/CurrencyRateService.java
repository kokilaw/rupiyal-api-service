package io.kokilaw.rupiyal.service;

import io.kokilaw.rupiyal.common.dto.CurrencyRateDTO;
import io.kokilaw.rupiyal.common.model.CurrencyRateType;

import java.util.List;

/**
 * Created by kokilaw on 2023-06-13
 */
public interface CurrencyRateService {

    void saveCurrencyRates(CurrencyRateType currencyRateType, List<CurrencyRateDTO> currencyRates);

}
