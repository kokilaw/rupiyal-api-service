package io.kokilaw.rupiyal.repository;

import io.kokilaw.rupiyal.repository.model.BankEntity;
import io.kokilaw.rupiyal.repository.model.BuyingRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by kokilaw on 2023-06-13
 */
@Repository
public interface BuyingRateRepository extends JpaRepository<BuyingRateEntity, Long> {
    Optional<BuyingRateEntity> findByBankAndCurrencyCodeAndDateAndRate(BankEntity bank, String currencyCode, LocalDate date, BigDecimal rate);
}
