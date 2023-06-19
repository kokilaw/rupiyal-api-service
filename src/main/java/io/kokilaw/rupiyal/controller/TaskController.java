package io.kokilaw.rupiyal.controller;

import io.kokilaw.rupiyal.common.request.TaskRequest;
import io.kokilaw.rupiyal.common.response.TaskResponse;
import io.kokilaw.rupiyal.processor.CurrencyFetchProcessorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
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
    public TaskResponse executeFetchTask(@RequestBody TaskRequest taskRequest){
        currencyFetchProcessorRegistry.currencyFetchProcessor(taskRequest.getProcessorType())
                .execute();
        return new TaskResponse();
    }

}
