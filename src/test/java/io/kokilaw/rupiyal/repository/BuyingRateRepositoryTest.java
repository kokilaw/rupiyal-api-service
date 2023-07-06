package io.kokilaw.rupiyal.repository;

import io.kokilaw.rupiyal.repository.model.BankEntity;
import io.kokilaw.rupiyal.repository.model.BuyingRateEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    @DisplayName("When new entry is saved, database and relevant attributes get updated")
    void whenEntryIsSaved_DatabaseGetsUpdated() {

        BankEntity bankEntity = bankRepository.findById("NTB")
                .orElseThrow(() -> new RuntimeException("Bank not found!"));

        BuyingRateEntity entryToBeSaved = BuyingRateEntity.builder()
                .bank(bankEntity)
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

}