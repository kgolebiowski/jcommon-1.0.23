package org.jfree.date;

import org.jfree.date.units.Month;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by kgolebiowski on 05/06/2017.
 */
public class SpreadsheetDateFactory extends DayDateFactory {

    @Override
    protected DayDate _makeDate(int ordinal) {
        return new SpreadsheetDate(ordinal);
    }

    @Override
    protected DayDate _makeDate(int day, Month month, int year) {
        return new SpreadsheetDate(day, month.index, year);
    }

    @Override
    protected DayDate _makeDate(int day, int month, int year) {
        return new SpreadsheetDate(day, month, year);
    }

    @Override
    protected DayDate _makeDate(Date date) {
        return this._makeDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    @Override
    protected DayDate _makeDate(LocalDate localDate) {
        return new SpreadsheetDate(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }

    @Override
    protected int _getMinimumYear() {
        return SpreadsheetDate.MINIMUM_YEAR_SUPPORTED;
    }

    @Override
    protected int _getMaximumYear() {
        return SpreadsheetDate.MAXIMUM_YEAR_SUPPORTED;
    }
}
