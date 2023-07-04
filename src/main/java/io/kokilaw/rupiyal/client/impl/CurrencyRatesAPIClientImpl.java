package io.kokilaw.rupiyal.client.impl;

import io.kokilaw.rupiyal.client.CurrencyRatesAPIClient;
import io.kokilaw.rupiyal.config.CurrencyRatesApiConfig;
import io.kokilaw.rupiyal.dto.CurrencyRateDTO;
import io.kokilaw.rupiyal.exception.CurrencyRatesAPIException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kokilaw on 2023-07-04
 */
@Component
public class CurrencyRatesAPIClientImpl implements CurrencyRatesAPIClient {

    private final RestTemplate restTemplate;
    private final CurrencyRatesApiConfig currencyRatesApiConfig;

    @Autowired
    public CurrencyRatesAPIClientImpl(
            @Qualifier("currencyRatesAPIHttpClient") RestTemplate restTemplate,
            CurrencyRatesApiConfig currencyRatesApiConfig) {
        this.restTemplate = restTemplate;
        this.currencyRatesApiConfig = currencyRatesApiConfig;
    }

    @Override
    public List<CurrencyRateDTO> getBuyingRates() {
        try {
            String apiUrl = currencyRatesApiConfig.getApiUrl().concat("?rateType=BUYING");
            ResponseEntity<BankRates[]> response = restTemplate.getForEntity(apiUrl, BankRates[].class);
            return mapToDTO(response.getBody());
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new CurrencyRatesAPIException(e.getMessage());
        }
    }

    @Override
    public List<CurrencyRateDTO> getSellingRates() {
        try {
            String apiUrl = currencyRatesApiConfig.getApiUrl().concat("?rateType=SELLING");
            ResponseEntity<BankRates[]> response = restTemplate.getForEntity(apiUrl, BankRates[].class);
            return mapToDTO(response.getBody());
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new CurrencyRatesAPIException(e.getMessage());
        }
    }

    private List<CurrencyRateDTO> mapToDTO(BankRates[] bankRatesArray) {
        List<BankRates> bankRatesList = Arrays.asList(bankRatesArray);
        return bankRatesList
                .stream()
                .flatMap(bankRates -> bankRates.rates
                        .stream()
                        .map(rate -> new TempRate(rate.currencyCode(), rate.rate(), bankRates.internalBankCode())))
                .filter(tempRate -> StringUtils.isNotEmpty(tempRate.rate()) && !tempRate.rate().contains("-"))
                .map(tempRate -> new CurrencyRateDTO(LocalDate.now(), new BigDecimal(tempRate.rate()), tempRate.currencyCode(), tempRate.bankCode()))
                .collect(Collectors.toList());
    }

    private record BankRates(String internalBankCode, List<Rate> rates) {

    }

    private record Rate(String currencyCode, String rate) {

    }

    private record TempRate(String currencyCode, String rate, String bankCode) {

    }

}
