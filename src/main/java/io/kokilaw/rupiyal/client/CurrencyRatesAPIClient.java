package io.kokilaw.rupiyal.client;

import io.kokilaw.rupiyal.dto.CurrencyRateDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by kokilaw on 2023-07-04
 */
public interface CurrencyRatesAPIClient {

    List<CurrencyRateDTO> getLatestBuyingRates();
    List<CurrencyRateDTO> getBuyingRates(LocalDate targetDate);
    List<CurrencyRateDTO> getLatestSellingRates();
    List<CurrencyRateDTO> getSellingRates(LocalDate targetDate);

}
