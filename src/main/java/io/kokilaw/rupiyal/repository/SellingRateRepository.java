package io.kokilaw.rupiyal.repository;

import io.kokilaw.rupiyal.repository.model.BankEntity;
import io.kokilaw.rupiyal.repository.model.SellingRateEntity;
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
public interface SellingRateRepository extends JpaRepository<SellingRateEntity, Long> {

    Optional<SellingRateEntity> findByBankAndCurrencyCodeAndDateAndRate(BankEntity bank, String currencyCode, LocalDate date, BigDecimal rate);

    @Query(value = "SELECT DISTINCT ON (bank_code, currency_code, date) * FROM selling_rate WHERE date = ?1 ORDER BY bank_code, currency_code, date, created_at DESC", nativeQuery = true)
    List<SellingRateEntity> getLastEntriesForTheDateGroupedByBankAndCurrency(String date);

}
