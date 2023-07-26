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
public class ExchangeRatesFetchProcessorRegistry {

    private final Map<ProcessorType, ExchangeRatesFetchProcessor> registry = new EnumMap<>(ProcessorType.class);

    public void register(ExchangeRatesFetchProcessor exchangeRatesFetchProcessor) {
        this.registry.put(exchangeRatesFetchProcessor.getType(), exchangeRatesFetchProcessor);
    }

    public ExchangeRatesFetchProcessor currencyFetchProcessor(ProcessorType type) {
        ExchangeRatesFetchProcessor exchangeRatesFetchProcessor = this.registry.get(type);
        if (exchangeRatesFetchProcessor == null) {
            throw new NotFoundException(String.format("No processor found for provided type - %s", type));
        }
        return exchangeRatesFetchProcessor;
    }

}
