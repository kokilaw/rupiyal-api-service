package io.kokilaw.rupiyal.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.time.LocalDate.parse;

/**
 * Created by kokilaw on 2023-07-18
 */
public class DateUtils {

    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMAT = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isValidDate(String dateString) {

        if (StringUtils.isEmpty(dateString)) {
            return false;
        }

        try {
            return parse(dateString).toString().equals(dateString);
        } catch (DateTimeParseException e) {
            return false;
        }

    }

    public static List<LocalDate> getDatesWithinPeriod(LocalDate from, LocalDate to) {

        if (to.isBefore(from)) {
            return Collections.emptyList();
        }

        long numOfDaysBetween = ChronoUnit.DAYS.between(from, to) + 1;
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(from::plusDays)
                .toList();

    }

    public static String getDateTimeWithSystemFormat(LocalDateTime dateTime) {
        return getDateTimeWithSystemFormat(dateTime, ZoneId.systemDefault());
    }

    public static String getDateTimeWithSystemFormat(LocalDateTime dateTime, ZoneId zoneId) {
        ZonedDateTime dateTimeWithZone = ZonedDateTime.of(dateTime, zoneId);
        return DEFAULT_DATE_TIME_FORMAT.format(dateTimeWithZone);
    }

}
