package io.kokilaw.rupiyal.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kokilaw on 2023-07-18
 */
class DateUtilsTest {

    @Test
    @DisplayName("Should return true when valid date is passed")
    void givenValidDate_thenReturnsTrue() {
        assertTrue(DateUtils.isValidDate("2022-03-31"));
    }

    @Test
    @DisplayName("Should return false when invalid date is passed")
    void givenInValidDate_thenReturnsFalse() {
        assertFalse(DateUtils.isValidDate("2022-1"));
        assertFalse(DateUtils.isValidDate(""));
        assertFalse(DateUtils.isValidDate(null));
    }

    @Test
    @DisplayName("Should return date list when valid date range is passed")
    void givenValidDateRange_thenReturnsDateList() {
        List<LocalDate> datesWithinPeriod = DateUtils.getDatesWithinPeriod(LocalDate.of(2023, 6, 30), LocalDate.of(2023, 7, 1));
        List<String> dateStings = datesWithinPeriod.stream().map(LocalDate::toString).toList();
        assertEquals(List.of("2023-06-30", "2023-07-01"), dateStings);
    }

    @Test
    @DisplayName("Should return single item list when same date is passed")
    void givenSameDateIsPassed_thenReturnsSingleListItem() {
        List<LocalDate> datesWithinPeriod = DateUtils.getDatesWithinPeriod(LocalDate.of(2023, 6, 30), LocalDate.of(2023, 6, 30));
        List<String> dateStings = datesWithinPeriod.stream().map(LocalDate::toString).toList();
        assertEquals(List.of("2023-06-30"), dateStings);
    }

    @Test
    @DisplayName("Should return empty list when invalid date range is passed")
    void givenInValidDateRange_thenReturnsEmptyList() {
        List<LocalDate> datesWithinPeriod = DateUtils.getDatesWithinPeriod(LocalDate.of(2023, 7, 1), LocalDate.of(2023, 6, 30));
        List<String> dateStings = datesWithinPeriod.stream().map(LocalDate::toString).toList();
        assertEquals(Collections.emptyList(), dateStings);

        System.out.println(DateUtils.getDateTimeWithSystemFormat(LocalDateTime.now()));
    }

    @Test
    @DisplayName("Should return formatted date time string with time zone attached")
    void givenLocalDateTimeInstance_shouldReturnFormattedStringWithTimeZone() {
        String formattedDateTime = DateUtils.getDateTimeWithSystemFormat(LocalDateTime.of(2023, 7, 11, 11, 1, 55), ZoneId.of("+05:30"));
        assertEquals("2023-07-11T11:01:55+05:30", formattedDateTime);
    }

    @Test
    @DisplayName("Should return correct back-dated date based on target date")
    void givenTargetData_shouldReturnCorrectBankDatedDate() {

        LocalDate CURRENT_DATE = LocalDate.now();
        LocalDate OLDER_DATE = LocalDate.of(2023, 5, 27);

        LocalDateTime currentDateScenario = DateUtils.getBackDatedTimeBasedOnTargetDate(CURRENT_DATE);
        Assertions.assertEquals(CURRENT_DATE.getYear(), currentDateScenario.getYear());
        Assertions.assertEquals(CURRENT_DATE.getMonthValue(), currentDateScenario.getMonthValue());
        Assertions.assertEquals(CURRENT_DATE.getDayOfMonth(), currentDateScenario.getDayOfMonth());

        LocalDateTime olderDateScenario = DateUtils.getBackDatedTimeBasedOnTargetDate(OLDER_DATE);
        Assertions.assertEquals(OLDER_DATE.getYear(), olderDateScenario.getYear());
        Assertions.assertEquals(OLDER_DATE.getMonthValue(), olderDateScenario.getMonthValue());
        Assertions.assertEquals(OLDER_DATE.getDayOfMonth(), olderDateScenario.getDayOfMonth());
    }

}