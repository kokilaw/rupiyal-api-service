package io.kokilaw.rupiyal.processor;

import io.kokilaw.rupiyal.common.model.ProcessorType;

/**
 * Created by kokilaw on 2023-06-13
 */
public interface CurrencyFetchProcessor {

    ProcessorType getType();

    void execute();

}
