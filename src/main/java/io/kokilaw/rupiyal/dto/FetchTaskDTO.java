package io.kokilaw.rupiyal.dto;


import java.time.LocalDate;

/**
 * Created by kokilaw on 2023-06-13
 */
public record FetchTaskDTO(ProcessorType processorType, LocalDate fromDate, LocalDate toDate) {
    public FetchTaskDTO(ProcessorType processorType) {
        this(processorType, null, null);
    }

}
