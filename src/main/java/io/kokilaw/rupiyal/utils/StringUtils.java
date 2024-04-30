package io.kokilaw.rupiyal.utils;

public class StringUtils {

    public static String sanitizeString(String value) {
        return value.replaceAll(" ", "");
    }

}
