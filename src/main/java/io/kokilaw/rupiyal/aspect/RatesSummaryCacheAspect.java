package io.kokilaw.rupiyal.aspect;

import io.kokilaw.rupiyal.dto.FetchTaskDTO;
import io.kokilaw.rupiyal.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kokilaw on 2023-07-27
 */
@Aspect
@Component
@Slf4j
public class RatesSummaryCacheAspect {

    private final ExecutorService cacheJob = Executors.newCachedThreadPool();
    private final ExchangeRateService exchangeRateService;

    @Autowired
    public RatesSummaryCacheAspect(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }


    @Around("@annotation(io.kokilaw.rupiyal.aspect.annotation.UpdateRatesSummaryCache)")
    public Object updateRateSummaryCache(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        Arrays.stream(joinPoint.getArgs()).findAny().ifPresent(arg -> {
            if (arg instanceof FetchTaskDTO fetchTaskDTO) {
                cacheJob.submit(() -> generateAndUpdateRatesSummaryCache(fetchTaskDTO));
            }
        });
        return proceed;
    }

    private void generateAndUpdateRatesSummaryCache(FetchTaskDTO fetchTaskDTO) {
        if (fetchTaskDTO.fromDate() == null) exchangeRateService.getLatestCurrencyRates();
    }

}
