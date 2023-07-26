package io.kokilaw.rupiyal.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by kokilaw on 2023-07-21
 */
public class PriceUtils {

    private PriceUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String formatPriceInDefaultFormat(BigDecimal price) {
        return price.setScale(4, RoundingMode.HALF_UP).toPlainString();
    }

}
