package io.kokilaw.rupiyal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
    }

}