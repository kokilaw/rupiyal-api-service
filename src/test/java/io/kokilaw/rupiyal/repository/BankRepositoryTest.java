package io.kokilaw.rupiyal.repository;

import io.kokilaw.rupiyal.repository.model.BankEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

/**
 * Created by kokilaw on 2023-06-13
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class BankRepositoryTest {

    @Autowired
    private BankRepository bankRepository;

    @Test
    @DisplayName("Testing whether initial data loaded successfully")
    void testWhetherInitialDataExists() {
        Optional<BankEntity> bankEntityOptional = bankRepository.findById("NTB");
        Assertions.assertTrue(bankEntityOptional.isPresent());
    }

}