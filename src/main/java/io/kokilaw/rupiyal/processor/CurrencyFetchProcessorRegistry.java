package io.kokilaw.rupiyal.processor;

import io.kokilaw.rupiyal.dto.ProcessorType;
import io.kokilaw.rupiyal.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by kokilaw on 2023-06-13
 */
@Component
public class CurrencyFetchProcessorRegistry {

    private final Map<ProcessorType, CurrencyFetchProcessor> registry = new EnumMap<>(ProcessorType.class);

    public void register(CurrencyFetchProcessor currencyFetchProcessor) {
        this.registry.put(currencyFetchProcessor.getType(), currencyFetchProcessor);
    }

    public CurrencyFetchProcessor currencyFetchProcessor(ProcessorType type) {
        CurrencyFetchProcessor currencyFetchProcessor = this.registry.get(type);
        if (currencyFetchProcessor == null) {
            throw new NotFoundException(String.format("No processor found for provided type - %s", type));
        }
        return currencyFetchProcessor;
    }

}
