package io.kokilaw.rupiyal.client;

import io.kokilaw.rupiyal.dto.CurrencyRateDTO;

import java.util.List;

/**
 * Created by kokilaw on 2023-07-04
 */
public interface CurrencyRatesAPIClient {

    List<CurrencyRateDTO> getBuyingRates();
    List<CurrencyRateDTO> getSellingRates();

}
