package io.kokilaw.rupiyal.mapper;

import io.kokilaw.rupiyal.dto.BankDTO;
import io.kokilaw.rupiyal.repository.model.BankEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kokilaw on 2023-07-04
 */
class BankMapperTest {

    @Test
    @DisplayName("Should return correct BankDTO when BankEntity is provided")
    public void givenBankEntity_whenMaps_thenProducesCorrectBankDto() {
        BankEntity bankEntity = BankEntity.builder()
                .bankCode("NTB")
                .longName("Nations Trust Bank")
                .shortName("NTB")
                .build();
        BankDTO bankDTO = BankMapper.INSTANCE.convert(bankEntity);
        assertEquals(bankDTO.bankCode(), bankEntity.getBankCode());
        assertEquals(bankDTO.shortName(), bankEntity.getShortName());
        assertEquals(bankDTO.longName(), bankEntity.getLongName());
    }

}