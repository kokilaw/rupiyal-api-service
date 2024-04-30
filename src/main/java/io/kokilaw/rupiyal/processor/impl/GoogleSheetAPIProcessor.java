package io.kokilaw.rupiyal.processor.impl;

import io.kokilaw.rupiyal.client.ExchangeRatesAPIClient;
import io.kokilaw.rupiyal.dto.ExchangeRateDTO;
import io.kokilaw.rupiyal.dto.ExchangeRateType;
import io.kokilaw.rupiyal.dto.FetchTaskDTO;
import io.kokilaw.rupiyal.dto.ProcessorType;
import io.kokilaw.rupiyal.processor.ExchangeRatesFetchProcessor;
import io.kokilaw.rupiyal.processor.ExchangeRatesFetchProcessorRegistry;
import io.kokilaw.rupiyal.service.ExchangeRateService;
import io.kokilaw.rupiyal.utils.DateUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kokilaw on 2023-06-13
 */
@Component
@Slf4j
public class GoogleSheetAPIProcessor implements ExchangeRatesFetchProcessor {

    private final ExchangeRatesFetchProcessorRegistry exchangeRatesFetchProcessorRegistry;
    private final ExchangeRateService exchangeRateService;
    private final ExchangeRatesAPIClient exchangeRatesAPIClient;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    public GoogleSheetAPIProcessor(
            ExchangeRatesFetchProcessorRegistry exchangeRatesFetchProcessorRegistry,
            ExchangeRateService exchangeRateService,
            ExchangeRatesAPIClient exchangeRatesAPIClient) {
        this.exchangeRatesFetchProcessorRegistry = exchangeRatesFetchProcessorRegistry;
        this.exchangeRateService = exchangeRateService;
        this.exchangeRatesAPIClient = exchangeRatesAPIClient;
    }

    @Override
    public ProcessorType getType() {
        return ProcessorType.GOOGLE_SHEET_API;
    }

    @Async
    @Override
    public void execute(FetchTaskDTO taskDTO) {
        executorService.execute(() -> {
            if (isLatestRatesFetchTask(taskDTO)) {
                executeLatestRatesFetchTask();
            } else {
                executeRatesFetchTaskForSpecificPeriod(taskDTO.fromDate(), taskDTO.toDate());
            }
        });
    }

    private void executeRatesFetchTaskForSpecificPeriod(LocalDate fromDate, LocalDate toDate) {

        List<LocalDate> datesWithinPeriod = DateUtils.getDatesWithinPeriod(fromDate, toDate);
        log.info("[START] Executing currency fetch task for period [{}][{}][{}]", datesWithinPeriod.size(), fromDate, toDate);

        for (LocalDate processingDate : datesWithinPeriod) {
            log.info("[START] Executing currency fetch task for date [{}]", processingDate);
            try {
                List<ExchangeRateDTO> buyingRates = exchangeRatesAPIClient.getBuyingRates(processingDate);
                List<ExchangeRateDTO> sellingRates = exchangeRatesAPIClient.getSellingRates(processingDate);
                exchangeRateService.saveCurrencyRates(ExchangeRateType.BUYING, buyingRates);
                exchangeRateService.saveCurrencyRates(ExchangeRateType.SELLING, sellingRates);
                log.info("Saving fetched currency rates for date[{}] buyingRates[{}] sellingRates[{}]", processingDate, buyingRates.size(), sellingRates.size());
            } catch (Exception e) {
                log.error("Error occurred while executing currency rates fetch for data [{}] - ", processingDate, e);
            }
            log.info("[END] Executing currency fetch task for date [{}]", processingDate);
        }

        log.info("[END] Executing currency fetch task for period [{}][{}][{}]", datesWithinPeriod.size(), fromDate, toDate);

    }

    private void executeLatestRatesFetchTask() {
        log.info("[START] Executing latest currency fetch task");
        List<ExchangeRateDTO> buyingRates = exchangeRatesAPIClient.getLatestBuyingRates();
        List<ExchangeRateDTO> sellingRates = exchangeRatesAPIClient.getLatestSellingRates();
        exchangeRateService.saveCurrencyRates(ExchangeRateType.BUYING, buyingRates);
        exchangeRateService.saveCurrencyRates(ExchangeRateType.SELLING, sellingRates);
        log.info("[END] Executing latest currency fetch task. buyingRates[{}] sellingRates[{}]", buyingRates.size(), sellingRates.size());
    }

    private boolean isLatestRatesFetchTask(FetchTaskDTO taskDTO) {
        return taskDTO.fromDate() == null || taskDTO.toDate() == null;
    }

    @PostConstruct
    public void register() {
        exchangeRatesFetchProcessorRegistry.register(this);
    }

}
