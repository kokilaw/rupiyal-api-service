package io.kokilaw.rupiyal.repository;

import io.kokilaw.rupiyal.repository.model.BankEntity;
import io.kokilaw.rupiyal.repository.model.SellingRateEntity;
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
class SellingRateRepositoryTest {

    @Autowired
    private SellingRateRepository sellingRateRepository;

    @Autowired
    private BankRepository bankRepository;

    @Test
    @DisplayName("When new entry is saved, database and relevant attributes get updated")
    void whenEntryIsSaved_DatabaseGetsUpdated() {

        BankEntity bankEntity = bankRepository.findById("NTB")
                .orElseThrow(() -> new RuntimeException("Bank not found!"));

        SellingRateEntity entryToBeSaved = SellingRateEntity.builder()
                .bankCode(bankEntity.getBankCode())
                .date(LocalDate.now())
                .currencyCode("USD")
                .rate(new BigDecimal("294.50"))
                .build();

        SellingRateEntity savedEntry = sellingRateRepository.saveAndFlush(entryToBeSaved);
        SellingRateEntity fetchedSavedEntry = sellingRateRepository.findById(savedEntry.getId())
                .orElseThrow(() -> new RuntimeException("Entry not found!"));
        assertEquals(0, entryToBeSaved.getRate().compareTo(fetchedSavedEntry.getRate()));
        assertNotNull(savedEntry.getUpdatedAt());
        assertNotNull(savedEntry.getCreatedAt());


    }

    @Test
    @DisplayName("When multiple entries are available for a single day, latest entry for the day is retrieved")
    @Sql({"/test-data/2-selling-rate-repository.sql"})
    void whenMultipleEntriesAvailableForSingleDate_LatestEntryForTheDayIsRetrieved() {
        List<SellingRateEntity> allEntries = sellingRateRepository.findAll();
        assertEquals(4, allEntries.size());
        List<SellingRateEntity> entries = sellingRateRepository.getLastEntriesForTheDateGroupedByBankAndCurrency(LocalDate.parse("2023-07-20"));
        assertEquals(3, entries.size());

        Optional<SellingRateEntity> usdEntry = entries.stream().filter(buyingRateEntity -> "USD".equals(buyingRateEntity.getCurrencyCode())).findAny();
        assertEquals(Boolean.TRUE, usdEntry.isPresent());
        usdEntry.ifPresent(sellingRateEntity -> assertEquals(0, usdEntry.get().getRate().compareTo(new BigDecimal("306.5410"))));
    }

}