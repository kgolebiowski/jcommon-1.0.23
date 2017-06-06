package org.jfree.date.units;

import org.jfree.date.DayDate;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by kgolebiowski on 06/06/2017.
 */
public enum Month {
    JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6), JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(10),
    NOVEMBER(11), DECEMBER(12);

    public final int index;

    Month(int index) {
        this.index = index;
    }

    public static Optional<Month> make(int monthIndex) {
        return Arrays.stream(Month.values())
                .filter(month -> month.index == monthIndex)
                .findAny();
    }
}
