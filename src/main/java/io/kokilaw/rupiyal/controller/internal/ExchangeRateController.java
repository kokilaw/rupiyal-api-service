package io.kokilaw.rupiyal.controller.internal;

import io.kokilaw.rupiyal.dto.DateExchangeRatesSummaryDTO;
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

    @GetMapping("summary")
    public ResponseEntity<DateExchangeRatesSummaryDTO> getSummaryForDate(@RequestParam(value = "targetDate", required = false) String date) {
        log.info("Request received to get exchange rates for date[{}]", date);
        DateExchangeRatesSummaryDTO currencyRatesForTheDate = StringUtils.isBlank(date)
                ? exchangeRateService.getLatestCurrencyRates()
                : exchangeRateService.getCurrencyRatesForTheDate(LocalDate.parse(date));
        return new ResponseEntity<>(currencyRatesForTheDate, HttpStatus.OK);
    }

}
