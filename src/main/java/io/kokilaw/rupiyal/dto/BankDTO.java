package io.kokilaw.rupiyal.dto;

import java.util.Map;

/**
 * Created by kokilaw on 2023-06-13
 */
public record BankDTO(
        String bankCode,
        String shortName,
        String longName,
        Map<String, String> logo) {
}