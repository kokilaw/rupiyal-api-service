package io.kokilaw.rupiyal;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.time.LocalDate.*;

/**
 * Created by kokilaw on 2023-07-18
 */
public class DateUtils {

    private DateUtils() {

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

}
