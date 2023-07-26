package io.kokilaw.rupiyal.processor;

import io.kokilaw.rupiyal.dto.FetchTaskDTO;
import io.kokilaw.rupiyal.dto.ProcessorType;

/**
 * Created by kokilaw on 2023-06-13
 */
public interface ExchangeRatesFetchProcessor {
    ProcessorType getType();
    void execute(FetchTaskDTO taskDTO);
}
