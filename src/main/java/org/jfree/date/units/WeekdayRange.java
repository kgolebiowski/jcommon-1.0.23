package org.jfree.date.units;

/**
 * Created by kgolebiowski on 06/06/2017.
 */
public enum WeekdayRange {
    PRECEDING, NEAREST, FOLLOWING;

    @Override
    public String toString() {
        String firstLetterUpperCase = name().substring(0, 1);
        String restLowerCase = name().substring(1, name().length()).toLowerCase();
        return firstLetterUpperCase + restLowerCase;
    }
}
