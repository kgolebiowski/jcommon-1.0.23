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

    protected static DayDate makeDate(int ordinal) {
        return factory._makeDate(ordinal);
    }

    protected static DayDate makeDate(int day, DayDate.Month month, int year) {
        return factory._makeDate(day, month, year);
    }

    protected static DayDate makeDate(int day, int month, int year) {
        return factory._makeDate(day, month, year);
    }

    protected static DayDate makeDate(Date date) {
        return factory._makeDate(date);
    }

    protected static DayDate makeDate(LocalDate localDate) {
        return factory._makeDate(localDate);
    }

    protected static int getMinimumYear() {
        return factory._getMinimumYear();
    }

    protected static int getMaximumYear() {
        return factory._getMaximumYear();
    }
}
