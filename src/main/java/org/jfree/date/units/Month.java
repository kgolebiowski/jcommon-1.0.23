package org.jfree.date.units;

import org.jfree.date.DayDate;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by kgolebiowski on 06/06/2017.
 */
public enum Month {
    JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6), JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(10),
    NOVEMBER(11), DECEMBER(12);

    public static final int[] LAST_DAY_OF_MONTH = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public final int index;

    Month(int index) {
        this.index = index;
    }

    public static String[] getMonthsNames() {
        return getMonthsNames(false);
    }

    public static String[] getMonthsNames(boolean shortened) {
        if (shortened) {
            return DayDate.DATE_FORMAT_SYMBOLS.getShortMonths();
        } else {
            return DayDate.DATE_FORMAT_SYMBOLS.getMonths();
        }
    }

    public static Optional<Month> make(final String monthAsString) {
        try {
            return make(Integer.parseInt(monthAsString.trim()));
        } catch (NumberFormatException e) {
            return Stream.of(DayDate.DATE_FORMAT_SYMBOLS.getShortMonths(), DayDate.DATE_FORMAT_SYMBOLS.getMonths())
                    .flatMap(names -> IntStream.range(0, names.length)
                            .filter(index -> names[index].equalsIgnoreCase(monthAsString.trim())).boxed())
                    .map(monthIndex -> make(monthIndex + 1)) // + 1 to transform array index to month ordinal
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .findAny();
        }
    }

    public static Optional<Month> make(int monthIndex) {
        return Arrays.stream(Month.values())
                .filter(month -> month.index == monthIndex)
                .findAny();
    }

    /**
     * Returns the number of the last day of the month, taking into account
     * leap years.
     *
     * @param yyyy  the year (in the range 1900 to 9999).
     * @return the number of the last day of the month.
     */
    public int lastDayOfMonth(int yyyy) {
        int result = LAST_DAY_OF_MONTH[index];
        if (this != FEBRUARY) {
            return result;
        } else if (DayDate.isLeapYear(yyyy)) {
            return result + 1;
        } else {
            return result;
        }
    }

    /**
     * @return a string representing the supplied month (taken from the
     * default locale).
     */
    public String toString() {
        return DayDate.DATE_FORMAT_SYMBOLS.getMonths()[index -1];
    }

    /**
     * @return an abbreviation representing the supplied month (taken
     * from the default locale).
     */
    public String toShortString() {
        return DayDate.DATE_FORMAT_SYMBOLS.getShortMonths()[this.index - 1];

    }
}
