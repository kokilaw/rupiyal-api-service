package io.kokilaw.rupiyal.mapper;

import io.kokilaw.rupiyal.dto.BankDTO;
import io.kokilaw.rupiyal.repository.model.BankEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kokilaw on 2023-07-04
 */
class BankMapperTest {

    @Test
    @DisplayName("Should return correct BankDTO when BankEntity is provided")
    void givenBankEntity_whenMaps_thenProducesCorrectBankDto() {
        BankEntity bankEntity = BankEntity.builder()
                .bankCode("NTB")
                .longName("Nations Trust Bank")
                .shortName("NTB")
                .logo(Map.of("defaultUrl", "IMAGE_URL"))
                .build();
        BankDTO bankDTO = BankMapper.INSTANCE.convert(bankEntity);
        assertEquals(bankDTO.bankCode(), bankEntity.getBankCode());
        assertEquals(bankDTO.shortName(), bankEntity.getShortName());
        assertEquals(bankDTO.longName(), bankEntity.getLongName());
        assertTrue(bankDTO.logo().containsKey("defaultUrl"));
        assertEquals("IMAGE_URL", bankDTO.logo().get("defaultUrl"));
    }

}