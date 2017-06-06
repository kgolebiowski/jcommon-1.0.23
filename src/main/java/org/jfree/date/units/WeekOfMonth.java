package org.jfree.date.units;

import org.jfree.date.DayDate;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by kgolebiowski on 06/06/2017.
 */
public enum WeekOfMonth {
    LAST, FIRST, SECOND, THIRD, FOURTH;

    public static Optional<WeekOfMonth> make(int index) {
        return Arrays.stream(WeekOfMonth.values())
                .filter(dayOfWeek -> dayOfWeek.ordinal() == index)
                .findAny();
    }
}
