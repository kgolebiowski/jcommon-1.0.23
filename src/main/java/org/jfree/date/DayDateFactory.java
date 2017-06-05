package org.jfree.date;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by kgolebiowski on 05/06/2017.
 */
public abstract class DayDateFactory {

    private static DayDateFactory factory = new SpreadsheetDateFactory();

    public static void setInstance(DayDateFactory factory) {
        DayDateFactory.factory = factory;
    }

    protected abstract DayDate _makeDate(int ordinal);

    protected abstract DayDate _makeDate(int day, DayDate.Month month, int year);

    protected abstract DayDate _makeDate(int day, int month, int year);

    protected abstract DayDate _makeDate(Date date);

    protected abstract DayDate _makeDate(LocalDate localDate);

    protected abstract int _getMinimumYear();

    protected abstract int _getMaximumYear();

    /**
     * Factory method that returns an instance of some concrete subclass of
     * {@link DayDate}.
     *
     * @param ordinal the serial number for the day (1 January 1900 = 2).
     * @return a instance of SerialDate.
     */
    public static DayDate makeDate(int ordinal) {
        return factory._makeDate(ordinal);
    }

    public static DayDate makeDate(int day, DayDate.Month month, int year) {
        return factory._makeDate(day, month, year);
    }

    /**
     * Factory method that returns an instance of some concrete subclass of
     * {@link DayDate}.
     *
     * @param day   the day (1-31).
     * @param month the month (1-12).
     * @param year  the year (in the range 1900 to 9999).
     * @return An instance of {@link DayDate}.
     */
    public static DayDate makeDate(int day, int month, int year) {
        return factory._makeDate(day, month, year);
    }

    /**
     * Factory method that returns an instance of a subclass of SerialDate.
     *
     * @param date A Java date object.
     * @return a instance of SerialDate.
     */
    public static DayDate makeDate(Date date) {
        return factory._makeDate(date);
    }

    public static DayDate makeDate(LocalDate localDate) {
        return factory._makeDate(localDate);
    }

    public static int getMinimumYear() {
        return factory._getMinimumYear();
    }

    public static int getMaximumYear() {
        return factory._getMaximumYear();
    }
}
