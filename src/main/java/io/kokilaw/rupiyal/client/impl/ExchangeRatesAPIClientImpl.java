package io.kokilaw.rupiyal.client.impl;

import io.kokilaw.rupiyal.client.ExchangeRatesAPIClient;
import io.kokilaw.rupiyal.config.ExchangeRatesApiConfig;
import io.kokilaw.rupiyal.dto.ExchangeRateDTO;
import io.kokilaw.rupiyal.exception.ExchangeRatesAPIException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static io.kokilaw.rupiyal.Constants.ASIA_COLOMBO_ZONE_ID;

/**
 * Created by kokilaw on 2023-07-04
 */
@Component
public class ExchangeRatesAPIClientImpl implements ExchangeRatesAPIClient {

    private final RestTemplate restTemplate;
    private final ExchangeRatesApiConfig exchangeRatesApiConfig;

    @Autowired
    public ExchangeRatesAPIClientImpl(
            @Qualifier("exchangeRatesAPIHttpClient") RestTemplate restTemplate,
            ExchangeRatesApiConfig exchangeRatesApiConfig) {
        this.restTemplate = restTemplate;
        this.exchangeRatesApiConfig = exchangeRatesApiConfig;
    }

    @Override
    public List<ExchangeRateDTO> getLatestBuyingRates() {
        try {
            String apiUrl = exchangeRatesApiConfig.getApiUrl().concat("?rateType=BUYING");
            ResponseEntity<BankRates[]> response = restTemplate.getForEntity(apiUrl, BankRates[].class);
            return mapToDTO(response.getBody());
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new ExchangeRatesAPIException(e.getMessage());
        }
    }

    @Override
    public List<ExchangeRateDTO> getBuyingRates(LocalDate targetDate) {
        try {
            String apiUrl = exchangeRatesApiConfig.getApiUrl()
                    .concat("?rateType=BUYING&targetDate=")
                    .concat(targetDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            ResponseEntity<BankRates[]> response = restTemplate.getForEntity(apiUrl, BankRates[].class);
            return mapToDTO(response.getBody(), targetDate);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new ExchangeRatesAPIException(e.getMessage());
        }
    }

    @Override
    public List<ExchangeRateDTO> getLatestSellingRates() {
        try {
            String apiUrl = exchangeRatesApiConfig.getApiUrl().concat("?rateType=SELLING");
            ResponseEntity<BankRates[]> response = restTemplate.getForEntity(apiUrl, BankRates[].class);
            return mapToDTO(response.getBody());
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new ExchangeRatesAPIException(e.getMessage());
        }
    }

    @Override
    public List<ExchangeRateDTO> getSellingRates(LocalDate targetDate) {
        try {
            String apiUrl = exchangeRatesApiConfig.getApiUrl()
                    .concat("?rateType=SELLING&targetDate=")
                    .concat(targetDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            ResponseEntity<BankRates[]> response = restTemplate.getForEntity(apiUrl, BankRates[].class);
            return mapToDTO(response.getBody(), targetDate);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new ExchangeRatesAPIException(e.getMessage());
        }
    }

    private List<ExchangeRateDTO> mapToDTO(BankRates[] bankRatesArray) {
        return mapToDTO(bankRatesArray, LocalDate.now(ASIA_COLOMBO_ZONE_ID));
    }

    private List<ExchangeRateDTO> mapToDTO(BankRates[] bankRatesArray, LocalDate targetDate) {
        List<BankRates> bankRatesList = Arrays.asList(bankRatesArray);
        return bankRatesList
                .stream()
                .flatMap(bankRates -> bankRates.rates
                        .stream()
                        .map(rate -> new TempRate(rate.currencyCode(), rate.rate(), bankRates.internalBankCode())))
                .filter(tempRate -> StringUtils.isNotEmpty(tempRate.rate()) && !tempRate.rate().contains("-"))
                .map(tempRate -> new ExchangeRateDTO(targetDate, new BigDecimal(tempRate.rate()), tempRate.currencyCode(), tempRate.bankCode()))
                .toList();
    }

    private record BankRates(String internalBankCode, List<Rate> rates) {

    }

    private record Rate(String currencyCode, String rate) {

    }

    private record TempRate(String currencyCode, String rate, String bankCode) {

    }

}
