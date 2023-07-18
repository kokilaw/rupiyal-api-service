package io.kokilaw.rupiyal.controller;

import io.kokilaw.rupiyal.dto.FetchTaskDTO;
import io.kokilaw.rupiyal.processor.CurrencyFetchProcessorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kokilaw on 2023-06-13
 */
@RestController
@RequestMapping("tasks")
public class TaskController {

    private final CurrencyFetchProcessorRegistry currencyFetchProcessorRegistry;

    @Autowired
    public TaskController(CurrencyFetchProcessorRegistry currencyFetchProcessorRegistry) {
        this.currencyFetchProcessorRegistry = currencyFetchProcessorRegistry;
    }

    @PostMapping("/fetch")
    public ResponseEntity<Void> executeFetchTask(@RequestBody FetchTaskDTO taskDTO){
        currencyFetchProcessorRegistry.currencyFetchProcessor(taskDTO.processorType())
                .execute(taskDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
