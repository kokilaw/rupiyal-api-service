package io.kokilaw.rupiyal.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kokilaw on 2023-07-21
 */
class PriceUtilsTest {

    @Test
    @DisplayName("Should format price in default format")
    void givenDifferentPrices_shouldFormatPriceInCorrectFormat() {
        assertEquals("2.3000", PriceUtils.formatPriceInDefaultFormat(new BigDecimal("2.3")));
        assertEquals("2.3334", PriceUtils.formatPriceInDefaultFormat(new BigDecimal("2.33335")));
        assertEquals("1111111112.3000", PriceUtils.formatPriceInDefaultFormat(new BigDecimal("1111111112.3")));
    }


}