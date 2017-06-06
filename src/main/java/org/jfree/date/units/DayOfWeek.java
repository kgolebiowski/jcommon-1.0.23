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

    public static Optional<DayOfWeek> make(final String dayOfWeekAsString) {
        return Stream.of(DayDate.DATE_FORMAT_SYMBOLS.getShortWeekdays(), DayDate.DATE_FORMAT_SYMBOLS.getWeekdays())
                .flatMap(names -> IntStream.range(0, names.length)
                        .filter(index -> names[index].equalsIgnoreCase(dayOfWeekAsString.trim())).boxed())
                .map(DayOfWeek::make)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny();
    }

    public String toString() {
        return DayDate.DATE_FORMAT_SYMBOLS.getWeekdays()[this.index];
    }
}
