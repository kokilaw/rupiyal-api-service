package io.kokilaw.rupiyal.service.impl;

import io.kokilaw.rupiyal.dto.ExchangeRateDTO;
import io.kokilaw.rupiyal.dto.ExchangeRateType;
import io.kokilaw.rupiyal.dto.DateExchangeRatesSummaryDTO;
import io.kokilaw.rupiyal.exception.NotFoundException;
import io.kokilaw.rupiyal.repository.BankRepository;
import io.kokilaw.rupiyal.repository.BuyingRateRepository;
import io.kokilaw.rupiyal.repository.SellingRateRepository;
import io.kokilaw.rupiyal.repository.model.BankEntity;
import io.kokilaw.rupiyal.repository.model.BuyingRateEntity;
import io.kokilaw.rupiyal.repository.model.SellingRateEntity;
import io.kokilaw.rupiyal.service.ExchangeRateService;
import io.kokilaw.rupiyal.utils.DateUtils;
import io.kokilaw.rupiyal.utils.PriceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kokilaw on 2023-06-13
 */
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final BankRepository bankRepository;
    private final BuyingRateRepository buyingRateRepository;
    private final SellingRateRepository sellingRateRepository;

    @Autowired
    public ExchangeRateServiceImpl(
            BankRepository bankRepository,
            BuyingRateRepository buyingRateRepository,
            SellingRateRepository sellingRateRepository) {
        this.bankRepository = bankRepository;
        this.buyingRateRepository = buyingRateRepository;
        this.sellingRateRepository = sellingRateRepository;
    }

    @Override
    public void saveCurrencyRates(ExchangeRateType exchangeRateType, List<ExchangeRateDTO> currencyRates) {
        if (ExchangeRateType.BUYING == exchangeRateType) {
            saveBuyingCurrencyRates(currencyRates);
        } else {
            saveSellingCurrencyRates(currencyRates);
        }
    }

    @Override
    public DateExchangeRatesSummaryDTO getCurrencyRatesForTheDate(LocalDate date) {

        List<BuyingRateEntity> buyingRates = buyingRateRepository.getLastEntriesForTheDateGroupedByBankAndCurrency(date.toString());
        List<SellingRateEntity> sellingRates = sellingRateRepository.getLastEntriesForTheDateGroupedByBankAndCurrency(date.toString());

        Map<String, List<DateExchangeRatesSummaryDTO.RateEntryDTO>> sellingRatesMap = new HashMap<>();
        sellingRates
                .stream()
                .map(SellingRateEntity::getCurrencyCode)
                .distinct()
                .forEach(currencyCode -> {
                    List<DateExchangeRatesSummaryDTO.RateEntryDTO> entries = sellingRates.stream()
                            .filter(sellingRateEntity -> sellingRateEntity.getCurrencyCode().equals(currencyCode))
                            .map(sellingRateEntity -> new DateExchangeRatesSummaryDTO.RateEntryDTO(
                                    sellingRateEntity.getBank().getBankCode(),
                                    PriceUtils.formatPriceInDefaultFormat(sellingRateEntity.getRate()),
                                    DateUtils.getDateTimeWithSystemFormat(sellingRateEntity.getUpdatedAt())
                            ))
                            .toList();
                    sellingRatesMap.put(currencyCode, entries);
                });

        Map<String, List<DateExchangeRatesSummaryDTO.RateEntryDTO>> buyingRatesMap = new HashMap<>();
        buyingRates
                .stream()
                .map(BuyingRateEntity::getCurrencyCode)
                .distinct()
                .forEach(currencyCode -> {
                    List<DateExchangeRatesSummaryDTO.RateEntryDTO> entries = buyingRates.stream()
                            .filter(buyingRateEntity -> buyingRateEntity.getCurrencyCode().equals(currencyCode))
                            .map(buyingRateEntity -> new DateExchangeRatesSummaryDTO.RateEntryDTO(
                                    buyingRateEntity.getBank().getBankCode(),
                                    PriceUtils.formatPriceInDefaultFormat(buyingRateEntity.getRate()),
                                    DateUtils.getDateTimeWithSystemFormat(buyingRateEntity.getUpdatedAt())
                            ))
                            .toList();
                    buyingRatesMap.put(currencyCode, entries);
                });


        return new DateExchangeRatesSummaryDTO(sellingRatesMap, buyingRatesMap);
    }

    private void saveSellingCurrencyRates(List<ExchangeRateDTO> currencyRates) {
        Map<String, List<ExchangeRateDTO>> ratesByBankCode = getRatesByBankCode(currencyRates);

        Map<String, BankEntity> bankEntityCache = new HashMap<>();
        ratesByBankCode.forEach((bankCode, rates) -> {

            if (!bankEntityCache.containsKey(bankCode)) {
                bankEntityCache.put(
                        bankCode,
                        bankRepository.findById(bankCode)
                                .orElseThrow(() -> new RuntimeException(String.format("Bank not found for code - %s", bankCode)))
                );
            }

            List<SellingRateEntity> sellingRateEntities = rates
                    .stream()
                    .map(rateDTO -> SellingRateEntity.builder()
                            .bank(bankEntityCache.get(bankCode))
                            .rate(rateDTO.rate())
                            .currencyCode(rateDTO.currencyCode())
                            .date(rateDTO.date())
                            .build())
                    .toList();
            sellingRateEntities.forEach(sellingRateEntity -> sellingRateRepository.findByBankAndCurrencyCodeAndDateAndRate(
                                    sellingRateEntity.getBank(),
                                    sellingRateEntity.getCurrencyCode(),
                                    sellingRateEntity.getDate(),
                                    sellingRateEntity.getRate()
                            )
                            .ifPresentOrElse(
                                    existingEntry -> {
                                        existingEntry.setUpdatedAt(LocalDateTime.now());
                                        sellingRateRepository.save(existingEntry);
                                    },
                                    () -> sellingRateRepository.save(sellingRateEntity)
                            )
            );
        });
    }

    private void saveBuyingCurrencyRates(List<ExchangeRateDTO> currencyRates) {
        Map<String, List<ExchangeRateDTO>> ratesByBankCode = getRatesByBankCode(currencyRates);

        Map<String, BankEntity> bankEntityCache = new HashMap<>();
        ratesByBankCode.forEach((bankCode, rates) -> {

            if (!bankEntityCache.containsKey(bankCode)) {
                bankEntityCache.put(
                        bankCode,
                        bankRepository.findById(bankCode)
                                .orElseThrow(() -> new NotFoundException(String.format("Bank not found for code - %s", bankCode)))
                );
            }

            List<BuyingRateEntity> buyingRateEntities = rates
                    .stream()
                    .map(rateDTO -> BuyingRateEntity.builder()
                            .bank(bankEntityCache.get(bankCode))
                            .rate(rateDTO.rate())
                            .currencyCode(rateDTO.currencyCode())
                            .date(rateDTO.date())
                            .build())
                    .toList();
            buyingRateEntities.forEach(buyingRateEntity -> buyingRateRepository.findByBankAndCurrencyCodeAndDateAndRate(buyingRateEntity.getBank(), buyingRateEntity.getCurrencyCode(), buyingRateEntity.getDate(), buyingRateEntity.getRate())
                    .ifPresentOrElse(
                            existingEntry -> {
                                existingEntry.setUpdatedAt(LocalDateTime.now());
                                buyingRateRepository.save(existingEntry);
                            },
                            () -> buyingRateRepository.save(buyingRateEntity)
                    )
            );
        });
    }

    private Map<String, List<ExchangeRateDTO>> getRatesByBankCode(List<ExchangeRateDTO> currencyRates) {
        Map<String, List<ExchangeRateDTO>> ratesByBankCode = new HashMap<>();
        currencyRates.forEach(currencyRateDTO -> {
            String bankCode = currencyRateDTO.bankCode();
            List<ExchangeRateDTO> ratesForBank;
            if (ratesByBankCode.containsKey(bankCode)) {
                ratesForBank = ratesByBankCode.get(bankCode);
            } else {
                ratesForBank = new ArrayList<>();
            }
            ratesForBank.add(currencyRateDTO);
            ratesByBankCode.put(bankCode, ratesForBank);
        });
        return ratesByBankCode;
    }

}
