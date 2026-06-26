package com.example.d308vacationplanner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ValidationUnitTest {

    @Test
    public void vacationEndDateAfterStartDate_returnsTrue() {
        assertTrue(isValidVacationDateRange("06/20/2026", "06/25/2026"));
    }

    @Test
    public void vacationEndDateBeforeStartDate_returnsFalse() {
        assertFalse(isValidVacationDateRange("06/25/2026", "06/20/2026"));
    }

    @Test
    public void reportSearchLessThanThreeCharacters_returnsFalse() {
        assertFalse(isValidReportSearch("s"));
    }

    @Test
    public void reportSearchThreeOrMoreCharacters_returnsTrue() {
        assertTrue(isValidReportSearch("sun"));
    }

    private boolean isValidVacationDateRange(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        sdf.setLenient(false);

        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            return start != null && end != null && !end.before(start);

        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isValidReportSearch(String searchText) {
        return searchText != null && searchText.trim().length() >= 3;
    }
}