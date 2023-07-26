package io.kokilaw.rupiyal.client;

import io.kokilaw.rupiyal.dto.ExchangeRateDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by kokilaw on 2023-07-04
 */
public interface ExchangeRatesAPIClient {

    List<ExchangeRateDTO> getLatestBuyingRates();
    List<ExchangeRateDTO> getBuyingRates(LocalDate targetDate);
    List<ExchangeRateDTO> getLatestSellingRates();
    List<ExchangeRateDTO> getSellingRates(LocalDate targetDate);

}
