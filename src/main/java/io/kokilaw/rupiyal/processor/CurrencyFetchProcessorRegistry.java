package io.kokilaw.rupiyal.processor;

import io.kokilaw.rupiyal.common.model.ProcessorType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kokilaw on 2023-06-13
 */
@Component
public class CurrencyFetchProcessorRegistry {

    private final Map<ProcessorType, CurrencyFetchProcessor> registry = new HashMap<>();

    public void register(CurrencyFetchProcessor currencyFetchProcessor) {
        this.registry.put(currencyFetchProcessor.getType(), currencyFetchProcessor);
    }

    public CurrencyFetchProcessor currencyFetchProcessor(ProcessorType type) {
        CurrencyFetchProcessor currencyFetchProcessor = this.registry.get(type);
        if (currencyFetchProcessor == null) {
            throw new RuntimeException(String.format("No processor found for provided type - %s", type));
        }
        return currencyFetchProcessor;
    }

}
