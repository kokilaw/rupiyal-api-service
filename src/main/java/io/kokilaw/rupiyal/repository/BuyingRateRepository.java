package io.kokilaw.rupiyal.repository;

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

    Optional<BuyingRateEntity> findByBankCodeAndCurrencyCodeAndDateAndRate(String bankCode, String currencyCode, LocalDate date, BigDecimal rate);

    @Query(value = "SELECT DISTINCT ON (bank_code, currency_code, date) * FROM buying_rate WHERE date = ?1 ORDER BY bank_code, currency_code, date, created_at DESC", nativeQuery = true)
    List<BuyingRateEntity> getLastEntriesForTheDateGroupedByBankAndCurrency(LocalDate date);

    @Query(value = "SELECT DISTINCT ON (bank_code, currency_code) * FROM buying_rate ORDER BY bank_code, currency_code, created_at DESC", nativeQuery = true)
    List<BuyingRateEntity> getLatestEntriesGroupedByBankCodeAndCurrencyCode();

    @Query(value = "SELECT DISTINCT ON (bank_code, currency_code, date) id,currency_code,rate,date,created_at,updated_at,bank_code FROM buying_rate where currency_code = ?1 AND date BETWEEN ?2 AND ?3 ORDER BY bank_code, currency_code, date, created_at DESC", nativeQuery = true)
    List<BuyingRateEntity> getRatesForCurrencyAndPeriod(String currencyCode, LocalDate fromDate, LocalDate currentDate);
}
