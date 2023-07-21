package io.kokilaw.rupiyal.repository;

import io.kokilaw.rupiyal.repository.model.BankEntity;
import io.kokilaw.rupiyal.repository.model.BuyingRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by kokilaw on 2023-06-13
 */
@Repository
public interface BuyingRateRepository extends JpaRepository<BuyingRateEntity, Long> {

    Optional<BuyingRateEntity> findByBankAndCurrencyCodeAndDateAndRate(BankEntity bank, String currencyCode, LocalDate date, BigDecimal rate);

    @Query(value = "SELECT DISTINCT ON (bank_code, currency_code, date) * FROM buying_rate WHERE date = ?1 ORDER BY bank_code, currency_code, date, created_at DESC", nativeQuery = true)
    List<BuyingRateEntity> getLastEntriesForTheDateGroupedByBankAndCurrency(LocalDate date);

}
