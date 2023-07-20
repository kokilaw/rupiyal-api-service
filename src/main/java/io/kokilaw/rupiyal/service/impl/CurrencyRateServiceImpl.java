package io.kokilaw.rupiyal.service.impl;

import io.kokilaw.rupiyal.dto.CurrencyRateDTO;
import io.kokilaw.rupiyal.dto.CurrencyRateType;
import io.kokilaw.rupiyal.exception.NotFoundException;
import io.kokilaw.rupiyal.repository.BankRepository;
import io.kokilaw.rupiyal.repository.BuyingRateRepository;
import io.kokilaw.rupiyal.repository.SellingRateRepository;
import io.kokilaw.rupiyal.repository.model.BankEntity;
import io.kokilaw.rupiyal.repository.model.BuyingRateEntity;
import io.kokilaw.rupiyal.repository.model.SellingRateEntity;
import io.kokilaw.rupiyal.service.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kokilaw on 2023-06-13
 */
@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private final BankRepository bankRepository;
    private final BuyingRateRepository buyingRateRepository;
    private final SellingRateRepository sellingRateRepository;

    @Autowired
    public CurrencyRateServiceImpl(
            BankRepository bankRepository,
            BuyingRateRepository buyingRateRepository,
            SellingRateRepository sellingRateRepository) {
        this.bankRepository = bankRepository;
        this.buyingRateRepository = buyingRateRepository;
        this.sellingRateRepository = sellingRateRepository;
    }

    @Override
    public void saveCurrencyRates(CurrencyRateType currencyRateType, List<CurrencyRateDTO> currencyRates) {
        if (CurrencyRateType.BUYING == currencyRateType) {
            saveBuyingCurrencyRates(currencyRates);
        } else {
            saveSellingCurrencyRates(currencyRates);
        }
    }

    private void saveSellingCurrencyRates(List<CurrencyRateDTO> currencyRates) {
        Map<String, List<CurrencyRateDTO>> ratesByBankCode = getRatesByBankCode(currencyRates);

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

    private void saveBuyingCurrencyRates(List<CurrencyRateDTO> currencyRates) {
        Map<String, List<CurrencyRateDTO>> ratesByBankCode = getRatesByBankCode(currencyRates);

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

    private Map<String, List<CurrencyRateDTO>> getRatesByBankCode(List<CurrencyRateDTO> currencyRates) {
        Map<String, List<CurrencyRateDTO>> ratesByBankCode = new HashMap<>();
        currencyRates.forEach(currencyRateDTO -> {
            String bankCode = currencyRateDTO.bankCode();
            List<CurrencyRateDTO> ratesForBank;
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
