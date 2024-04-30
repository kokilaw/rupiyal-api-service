package io.kokilaw.rupiyal.controller.internal;

import io.kokilaw.rupiyal.aspect.annotation.UpdateRatesSummaryCache;
import io.kokilaw.rupiyal.dto.FetchTaskDTO;
import io.kokilaw.rupiyal.processor.ExchangeRatesFetchProcessorRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by kokilaw on 2023-06-13
 */
@RestController
@RequestMapping("internal/tasks")
@Slf4j
public class TaskController {

    private final ExchangeRatesFetchProcessorRegistry exchangeRatesFetchProcessorRegistry;

    @Autowired
    public TaskController(ExchangeRatesFetchProcessorRegistry exchangeRatesFetchProcessorRegistry) {
        this.exchangeRatesFetchProcessorRegistry = exchangeRatesFetchProcessorRegistry;
    }

    @PostMapping("/fetch")
    public ResponseEntity<Map<String, String>> executeFetchTask(@RequestBody FetchTaskDTO taskDTO) {
        log.info("Fetch task received - [{}]", taskDTO);
        exchangeRatesFetchProcessorRegistry.currencyFetchProcessor(taskDTO.processorType())
                .execute(taskDTO);
        log.info("Fetch task submitted for execution - [{}]", taskDTO);
        return new ResponseEntity<>(Map.of("submitted", "true"), HttpStatus.OK);
    }

}
