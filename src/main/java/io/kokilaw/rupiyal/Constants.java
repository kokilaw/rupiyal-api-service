package io.kokilaw.rupiyal;

import java.time.ZoneId;

/**
 * Created by kokilaw on 2023-07-23
 */
public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }


    public static final ZoneId ASIA_COLOMBO_ZONE_ID = ZoneId.of("+05:30");

}
