package io.kokilaw.rupiyal.processor.impl;

import io.kokilaw.rupiyal.client.CurrencyRatesAPIClient;
import io.kokilaw.rupiyal.dto.CurrencyRateDTO;
import io.kokilaw.rupiyal.dto.CurrencyRateType;
import io.kokilaw.rupiyal.dto.FetchTaskDTO;
import io.kokilaw.rupiyal.dto.ProcessorType;
import io.kokilaw.rupiyal.processor.CurrencyFetchProcessor;
import io.kokilaw.rupiyal.processor.CurrencyFetchProcessorRegistry;
import io.kokilaw.rupiyal.service.CurrencyRateService;
import io.kokilaw.rupiyal.utils.DateUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by kokilaw on 2023-06-13
 */
@Component
@Slf4j
public class GoogleSheetAPIProcessor implements CurrencyFetchProcessor {

    private final CurrencyFetchProcessorRegistry currencyFetchProcessorRegistry;
    private final CurrencyRateService currencyRateService;
    private final CurrencyRatesAPIClient currencyRatesAPIClient;

    @Autowired
    public GoogleSheetAPIProcessor(
            CurrencyFetchProcessorRegistry currencyFetchProcessorRegistry,
            CurrencyRateService currencyRateService,
            CurrencyRatesAPIClient currencyRatesAPIClient) {
        this.currencyFetchProcessorRegistry = currencyFetchProcessorRegistry;
        this.currencyRateService = currencyRateService;
        this.currencyRatesAPIClient = currencyRatesAPIClient;
    }

    @Override
    public ProcessorType getType() {
        return ProcessorType.GOOGLE_SHEET_API;
    }

    @Override
    public void execute(FetchTaskDTO taskDTO) {
        if (isLatestRatesFetchTask(taskDTO)) {
            executeLatestRatesFetchTask();
        } else {
            executeRatesFetchTaskForSpecificPeriod(taskDTO.fromDate(), taskDTO.toDate());
        }
    }

    private void executeRatesFetchTaskForSpecificPeriod(LocalDate fromDate, LocalDate toDate) {

        List<LocalDate> datesWithinPeriod = DateUtils.getDatesWithinPeriod(fromDate, toDate);
        log.info("[START] Executing currency fetch task for period [{}][{}][{}]", datesWithinPeriod.size(), fromDate, toDate);

        for (LocalDate processingDate : datesWithinPeriod) {
            log.info("[START] Executing currency fetch task for date [{}]", processingDate);
            try {
                List<CurrencyRateDTO> buyingRates = currencyRatesAPIClient.getBuyingRates(processingDate);
                List<CurrencyRateDTO> sellingRates = currencyRatesAPIClient.getSellingRates(processingDate);
                currencyRateService.saveCurrencyRates(CurrencyRateType.BUYING, buyingRates);
                currencyRateService.saveCurrencyRates(CurrencyRateType.SELLING, sellingRates);
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
        List<CurrencyRateDTO> buyingRates = currencyRatesAPIClient.getLatestBuyingRates();
        List<CurrencyRateDTO> sellingRates = currencyRatesAPIClient.getLatestSellingRates();
        currencyRateService.saveCurrencyRates(CurrencyRateType.BUYING, buyingRates);
        currencyRateService.saveCurrencyRates(CurrencyRateType.SELLING, sellingRates);
        log.info("[END] Executing latest currency fetch task. buyingRates[{}] sellingRates[{}]", buyingRates.size(), sellingRates.size());
    }

    private boolean isLatestRatesFetchTask(FetchTaskDTO taskDTO) {
        return taskDTO.fromDate() == null || taskDTO.toDate() == null;
    }

    @PostConstruct
    public void register() {
        currencyFetchProcessorRegistry.register(this);
    }

}
