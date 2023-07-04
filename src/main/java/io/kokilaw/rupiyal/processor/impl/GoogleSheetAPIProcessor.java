package io.kokilaw.rupiyal.processor.impl;

import io.kokilaw.rupiyal.dto.ProcessorType;
import io.kokilaw.rupiyal.processor.CurrencyFetchProcessor;
import io.kokilaw.rupiyal.processor.CurrencyFetchProcessorRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by kokilaw on 2023-06-13
 */
@Component
public class GoogleSheetAPIProcessor implements CurrencyFetchProcessor {

    private final CurrencyFetchProcessorRegistry currencyFetchProcessorRegistry;

    @Autowired
    public GoogleSheetAPIProcessor(CurrencyFetchProcessorRegistry currencyFetchProcessorRegistry) {
        this.currencyFetchProcessorRegistry = currencyFetchProcessorRegistry;
    }

    @Override
    public ProcessorType getType() {
        return ProcessorType.GOOGLE_SHEET_API;
    }

    @Override
    public void execute() {

    }

    @PostConstruct
    public void register() {
        currencyFetchProcessorRegistry.register(this);
    }

}
