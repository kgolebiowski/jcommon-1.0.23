package org.jfree.date.units;

import org.jfree.date.DayDate;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    public static Optional<DayOfWeek> make(final String s) {
        return Stream.of(DayDate.DATE_FORMAT_SYMBOLS.getShortWeekdays(), DayDate.DATE_FORMAT_SYMBOLS.getWeekdays())
                .flatMap(names -> IntStream.range(0, names.length)
                        .filter(index -> names[index].equalsIgnoreCase(s.trim())).boxed())
                .map(DayOfWeek::make)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny();
    }

    /**
     * Returns a string representing the supplied day-of-the-week.
     * <p>
     * Need to find a better approach.
     *
     * @return a string representing the supplied day-of-the-week.
     */
    public String toString() {
        final String[] weekdays = DayDate.DATE_FORMAT_SYMBOLS.getWeekdays();
        return weekdays[this.index];
    }
}
