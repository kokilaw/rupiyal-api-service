package io.kokilaw.rupiyal.controller;

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
import java.time.ZoneId;

/**
 * Created by kokilaw on 2023-07-21
 */
@RestController
@RequestMapping("exchange-rates")
@Slf4j
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public ResponseEntity<DateExchangeRatesSummaryDTO> getSummaryForDate(@RequestParam(value = "targetDate", required = false) String date) {
        log.info("Request received to get exchange rates for date[{}]", date);
        LocalDate targetDate = StringUtils.isBlank(date)
                ? LocalDate.now(ZoneId.of("Asia/Kolkata"))
                : LocalDate.parse(date);
        DateExchangeRatesSummaryDTO currencyRatesForTheDate = exchangeRateService.getCurrencyRatesForTheDate(targetDate);
        return new ResponseEntity<>(currencyRatesForTheDate, HttpStatus.OK);
    }

}
