package org.jfree.date.units;

import org.jfree.date.DayDate;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;

/**
 * Created by kgolebiowski on 06/06/2017.
 */
public enum DayOfWeek {
    MONDAY(Calendar.MONDAY), TUESDAY(Calendar.TUESDAY), WEDNESDAY(Calendar.WEDNESDAY), THURSDAY(Calendar.THURSDAY),
    FRIDAY(Calendar.FRIDAY), SATURDAY(Calendar.SATURDAY), SUNDAY(Calendar.SUNDAY);

    public final int index;

    DayOfWeek(int dayOfWeekIndex) {
        this.index = dayOfWeekIndex;
    }

    public static Optional<DayOfWeek> make(int dayOfWeekIndex) {
        return Arrays.stream(DayOfWeek.values())
                .filter(dayOfWeek -> dayOfWeek.index == dayOfWeekIndex)
                .findAny();
    }
}
