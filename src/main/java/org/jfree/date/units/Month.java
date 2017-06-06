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

    public final int index;

    Month(int index) {
        this.index = index;
    }

    /**
     * Returns an array of month names.
     *
     * @return an array of month names.
     */
    public static String[] getMonths() {
        return getMonths(false);

    }

    /**
     * Returns an array of month names.
     *
     * @param shortened a flag indicating that shortened month names should
     *                  be returned.
     * @return an array of month names.
     */
    public static String[] getMonths(final boolean shortened) {
        if (shortened) {
            return DayDate.DATE_FORMAT_SYMBOLS.getShortMonths();
        } else {
            return DayDate.DATE_FORMAT_SYMBOLS.getMonths();
        }

    }

    public static Optional<Month> make(final String s) {
        try {
            return make(Integer.parseInt(s.trim()));
        } catch (NumberFormatException e) {
            return Stream.of(DayDate.DATE_FORMAT_SYMBOLS.getShortMonths(), DayDate.DATE_FORMAT_SYMBOLS.getMonths())
                    .flatMap(names -> IntStream.range(0, names.length)
                            .filter(index -> names[index].equalsIgnoreCase(s.trim())).boxed())
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
     * Returns a string representing the supplied month.
     * <p>
     * The string returned is the long form of the month name taken from the
     * default locale.
     *
     * @return a string representing the supplied month.
     */
    public String toString() {
        return toString(false);
    }

    /**
     * Returns a string representing the supplied month.
     * <p>
     * The string returned is the long or short form of the month name taken
     * from the default locale.
     *
     * @param shortened if <code>true</code> return the abbreviation of the
     *                  month.
     * @return a string representing the supplied month.
     */
    public String toString(boolean shortened) {
        final String[] months;

        if (shortened) {
            months = DayDate.DATE_FORMAT_SYMBOLS.getShortMonths();
        } else {
            months = DayDate.DATE_FORMAT_SYMBOLS.getMonths();
        }

        return months[this.index - 1];

    }
}
