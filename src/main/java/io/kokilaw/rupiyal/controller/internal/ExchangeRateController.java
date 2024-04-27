package io.kokilaw.rupiyal.controller.internal;

import io.kokilaw.rupiyal.dto.DateExchangeRatesSummaryDTO;
import io.kokilaw.rupiyal.dto.ExtendedRateEntryDTO;
import io.kokilaw.rupiyal.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by kokilaw on 2023-07-21
 */
@RestController
@RequestMapping("internal/exchange-rates")
@Slf4j
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/buying")
    public ResponseEntity<Map<String, List<ExtendedRateEntryDTO>>> getBuyingRates(
            @RequestParam(value = "currencyCode") String currencyCode,
            @RequestParam(value = "lastNumberOfDays", required = false, defaultValue = "1") String lastNumberOfDays
    ) {
        log.info("[BUYING] Request received to get exchange rates for currencyCode[{}] lastNumberOfDays[{}]", currencyCode, lastNumberOfDays);
        Map<String, List<ExtendedRateEntryDTO>> entries = exchangeRateService.getBuyingRates(currencyCode, Integer.parseInt(lastNumberOfDays));
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("/selling")
    public ResponseEntity<Map<String, List<ExtendedRateEntryDTO>>> getSellingRates(
            @RequestParam(value = "currencyCode") String currencyCode,
            @RequestParam(value = "lastNumberOfDays", required = false, defaultValue = "1") String lastNumberOfDays
    ) {
        log.info("[SELLING] Request received to get exchange rates for currencyCode[{}] lastNumberOfDays[{}]", currencyCode, lastNumberOfDays);
        Map<String, List<ExtendedRateEntryDTO>> entries = exchangeRateService.getSellingRates(currencyCode, Integer.parseInt(lastNumberOfDays));
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("summary")
    public ResponseEntity<DateExchangeRatesSummaryDTO> getSummaryForDate(@RequestParam(value = "targetDate", required = false) String date) {
        log.info("Request received to get exchange rates for date[{}]", date);
        DateExchangeRatesSummaryDTO currencyRatesForTheDate = StringUtils.isBlank(date)
                ? exchangeRateService.getLatestCurrencyRates()
                : exchangeRateService.getCurrencyRatesForTheDate(LocalDate.parse(date));
        return new ResponseEntity<>(currencyRatesForTheDate, HttpStatus.OK);
    }

}
