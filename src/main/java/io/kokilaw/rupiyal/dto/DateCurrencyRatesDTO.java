package io.kokilaw.rupiyal.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by kokilaw on 2023-07-21
 */
public record DateCurrencyRatesDTO(Map<String, List<RateEntryDTO>> sellingRates,
                                   Map<String, List<RateEntryDTO>> buyingRates) {

    public record RateEntryDTO(String bankCode, String rate, String lastUpdated) {
    }

}