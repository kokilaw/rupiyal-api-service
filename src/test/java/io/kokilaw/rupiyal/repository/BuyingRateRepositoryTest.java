package io.kokilaw.rupiyal.repository;

import io.kokilaw.rupiyal.repository.model.BankEntity;
import io.kokilaw.rupiyal.repository.model.BuyingRateEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by kokilaw on 2023-06-13
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class BuyingRateRepositoryTest {

    @Autowired
    private BuyingRateRepository buyingRateRepository;

    @Autowired
    private BankRepository bankRepository;

    @Test
    @Disabled("Disabled because @CreateTimestamp and @UpdateTimestamp was removed from BuyingRateEntity")
    @DisplayName("When new entry is saved, database and relevant attributes get updated")
    void whenEntryIsSaved_DatabaseGetsUpdated() {

        BankEntity bankEntity = bankRepository.findById("NTB")
                .orElseThrow(() -> new RuntimeException("Bank not found!"));

        BuyingRateEntity entryToBeSaved = BuyingRateEntity.builder()
                .bankCode(bankEntity.getBankCode())
                .date(LocalDate.now())
                .currencyCode("USD")
                .rate(new BigDecimal("294.50"))
                .build();

        BuyingRateEntity savedEntry = buyingRateRepository.saveAndFlush(entryToBeSaved);
        BuyingRateEntity fetchedSavedEntry = buyingRateRepository.findById(savedEntry.getId())
                .orElseThrow(() -> new RuntimeException("Entry not found!"));
        assertEquals(0, entryToBeSaved.getRate().compareTo(fetchedSavedEntry.getRate()));
        assertNotNull(savedEntry.getUpdatedAt());
        assertNotNull(savedEntry.getCreatedAt());


    }

    @Test
    @DisplayName("When multiple entries are available for a single day, latest entry for the day is retrieved")
    @Sql({"/test-data/buying-rate-repository-test-data-1.sql"})
    void whenMultipleEntriesAvailableForSingleDate_LatestEntryForTheDayIsRetrieved() {
        List<BuyingRateEntity> allEntries = buyingRateRepository.findAll();
        assertEquals(4, allEntries.size());
        List<BuyingRateEntity> entries = buyingRateRepository.getLastEntriesForTheDateGroupedByBankAndCurrency(LocalDate.parse("2023-07-20"));
        assertEquals(3, entries.size());

        Optional<BuyingRateEntity> usdEntry = entries.stream().filter(buyingRateEntity -> "USD".equals(buyingRateEntity.getCurrencyCode())).findAny();
        assertEquals(Boolean.TRUE, usdEntry.isPresent());
        usdEntry.ifPresent(buyingRateEntity -> assertEquals(0, usdEntry.get().getRate().compareTo(new BigDecimal("306.5410"))));
    }

    @Test
    @DisplayName("Given multiple rates over multiple period, latest rates are fetched when queried")
    @Sql({"/test-data/buying-rate-repository-test-data-2.sql"})
    void givenMultipleRatesOverMultipleData_whenLatestRatesAreQueried_latestRatesAreFetched() {
        List<BuyingRateEntity> allEntries = buyingRateRepository.findAll();
        assertEquals(8, allEntries.size());
        List<BuyingRateEntity> entries = buyingRateRepository.getLatestEntriesGroupedByBankCodeAndCurrencyCode();
        assertEquals(3, entries.size());

        Optional<BuyingRateEntity> usdEntry = entries.stream().filter(buyingRateEntity -> "USD".equals(buyingRateEntity.getCurrencyCode())).findAny();
        assertEquals(Boolean.TRUE, usdEntry.isPresent());
        usdEntry.ifPresent(buyingRateEntity -> assertEquals("304.5410", usdEntry.get().getRate().toPlainString()));
    }

    @Test
    @DisplayName("Given multiple rates over multiple period, latest rates are fetched over given period")
    @Sql({"/test-data/buying-rate-repository-test-data-2.sql"})
    void givenMultipleRatesOverMultipleData_whenLatestRatesAreQueried_latestRatesAreFetchedOverGivenPeriod() {
        List<BuyingRateEntity> allEntries = buyingRateRepository.findAll();
        assertEquals(8, allEntries.size());

        List<BuyingRateEntity> results = buyingRateRepository
                .getRatesForCurrencyAndPeriod("USD", LocalDate.parse("2023-07-20"), LocalDate.parse("2023-07-21"));
        assertEquals(2, results.size());

    }

}