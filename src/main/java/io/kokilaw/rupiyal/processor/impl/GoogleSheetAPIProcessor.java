package io.kokilaw.rupiyal.processor.impl;

import io.kokilaw.rupiyal.client.CurrencyRatesAPIClient;
import io.kokilaw.rupiyal.dto.CurrencyRateDTO;
import io.kokilaw.rupiyal.dto.CurrencyRateType;
import io.kokilaw.rupiyal.dto.ProcessorType;
import io.kokilaw.rupiyal.processor.CurrencyFetchProcessor;
import io.kokilaw.rupiyal.processor.CurrencyFetchProcessorRegistry;
import io.kokilaw.rupiyal.service.CurrencyRateService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kokilaw on 2023-06-13
 */
@Component
public class GoogleSheetAPIProcessor implements CurrencyFetchProcessor {

    private final CurrencyFetchProcessorRegistry currencyFetchProcessorRegistry;
    private final CurrencyRateService currencyRateService;
    private final CurrencyRatesAPIClient currencyRatesAPIClient;

    @Autowired
    public GoogleSheetAPIProcessor(
            CurrencyFetchProcessorRegistry currencyFetchProcessorRegistry,
            CurrencyRateService currencyRateService,
            CurrencyRatesAPIClient currencyRatesAPIClient) {
        this.currencyFetchProcessorRegistry = currencyFetchProcessorRegistry;
        this.currencyRateService = currencyRateService;
        this.currencyRatesAPIClient = currencyRatesAPIClient;
    }

    @Override
    public ProcessorType getType() {
        return ProcessorType.GOOGLE_SHEET_API;
    }

    @Override
    public void execute() {
        List<CurrencyRateDTO> buyingRates = currencyRatesAPIClient.getBuyingRates();
        List<CurrencyRateDTO> sellingRates = currencyRatesAPIClient.getSellingRates();
        currencyRateService.saveCurrencyRates(CurrencyRateType.BUYING, buyingRates);
        currencyRateService.saveCurrencyRates(CurrencyRateType.SELLING, sellingRates);
    }

    @PostConstruct
    public void register() {
        currencyFetchProcessorRegistry.register(this);
    }

}
