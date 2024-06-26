package io.kokilaw.rupiyal.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by kokilaw on 2023-07-21
 */
public record DateExchangeRatesSummaryDTO(Map<String, List<RateEntryDTO>> sellingRates,
                                          Map<String, List<RateEntryDTO>> buyingRates,
                                          List<BankDTO> banks) {

    public record RateEntryDTO(String bankCode, String rate, String lastUpdated) {
    }

}