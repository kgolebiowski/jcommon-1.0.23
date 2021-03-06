/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
 * 
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ---------------
 * SerialDate.java
 * ---------------
 * (C) Copyright 2001-2006, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: SerialDate.java,v 1.9 2011/10/17 20:08:22 mungady Exp $
 *
 * Changes (from 11-Oct-2001)
 * --------------------------
 * 11-Oct-2001 : Re-organised the class and moved it to new package 
 *               com.jrefinery.date (DG);
 * 05-Nov-2001 : Added a getDescription() method, and eliminated NotableDate 
 *               class (DG);
 * 12-Nov-2001 : IBD requires setDescription() method, now that NotableDate 
 *               class is gone (DG);  Changed getPreviousDayOfWeek(), 
 *               getFollowingDayOfWeek() and getNearestDayOfWeek() to correct 
 *               bugs (DG);
 * 05-Dec-2001 : Fixed bug in SpreadsheetDate class (DG);
 * 29-May-2002 : Moved the month constants into a separate interface 
 *               (MonthConstants) (DG);
 * 27-Aug-2002 : Fixed bug in addMonths() method, thanks to N???levka Petr (DG);
 * 03-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 13-Mar-2003 : Implemented Serializable (DG);
 * 29-May-2003 : Fixed bug in addMonths method (DG);
 * 04-Sep-2003 : Implemented Comparable.  Updated the isInRange javadocs (DG);
 * 05-Jan-2005 : Fixed bug in addYears() method (1096282) (DG);
 * 
 */

package org.jfree.date;

import org.jfree.date.units.DayOfWeek;
import org.jfree.date.units.Month;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * An abstract class that defines our requirements for manipulating dates,
 * without tying down a particular implementation.
 * <p>
 * Requirement 1 : match at least what Excel does for dates;
 * Requirement 2 : the date represented by the class is immutable;
 * <p>
 * Why not just use java.util.Date?  We will, when it makes sense.  At times,
 * java.util.Date can be *too* precise - it represents an instant in time,
 * accurate to 1/1000th of a second (with the date itself depending on the
 * time-zone).  Sometimes we just want to represent a particular day (e.g. 21
 * January 2015) without concerning ourselves about the time of day, or the
 * time-zone, or anything else.  That's what we've defined SerialDate for.
 * <p>
 * You can call getInstance() to get a concrete subclass of SerialDate,
 * without worrying about the exact implementation.
 *
 * @author David Gilbert
 */
public abstract class DayDate implements Comparable, Serializable {

    private static final long serialVersionUID = -293716040467423637L;

    /**
     * Date format symbols.
     */
    public static final DateFormatSymbols
            DATE_FORMAT_SYMBOLS = new SimpleDateFormat().getDateFormatSymbols();

    /**
     * The number of days in a year up to the end of the preceding month.
     */
    static final int[] AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH =
            {0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};

    /**
     * The number of days in a leap year up to the end of the preceding month.
     */
    static final int[]
            LEAP_YEAR_AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH =
            {0, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};

    /**
     * Default constructor.
     */
    protected DayDate() {
    }

    /**
     * Determines whether or not the specified year is a leap year.
     *
     * @param yyyy the year (in the range 1900 to 9999).
     * @return <code>true</code> if the specified year is a leap year.
     */
    public static boolean isLeapYear(final int yyyy) {
        if ((yyyy % 4) != 0) {
            return false;
        } else if ((yyyy % 400) == 0) {
            return true;
        } else if ((yyyy % 100) == 0) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Returns the number of leap years from 1900 to the specified year
     * INCLUSIVE.
     * <p>
     * Note that 1900 is not a leap year.
     *
     * @param yyyy the year (in the range 1900 to 9999).
     * @return the number of leap years from 1900 to the specified year.
     */
    public static int leapYearCount(final int yyyy) {
        int leap4 = (yyyy - 1896) / 4;
        int leap100 = (yyyy - 1800) / 100;
        int leap400 = (yyyy - 1600) / 400;
        return leap4 - leap100 + leap400;

    }

    /**
     * Creates a new date by adding the specified number of days to the base
     * date.
     *
     * @param days the number of days to add (can be negative).
     * @return a new date.
     */
    public DayDate plusDays(int days) {
        return DayDateFactory.makeDate(this.getOrdinalDay() + days);
    }

    /**
     * Creates a new date by adding the specified number of months to the base
     * date.
     * <p>
     * If the base date is close to the end of the month, the day on the result
     * may be adjusted slightly:  31 May + 1 month = 30 June.
     *
     * @param months the number of months to add (can be negative).
     * @return a new date.
     */
    public DayDate plusMonths(int months) {
        int yy = (12 * this.getYear() + this.getMonth().index + months - 1) / 12;
        int mm = (12 * this.getYear() + this.getMonth().index + months - 1) % 12 + 1;
        int dd = Math.min(
                this.getDayOfMonth(), Month.make(mm).get().lastDayOfMonth(yy)
        );
        return DayDateFactory.makeDate(dd, mm, yy);
    }

    /**
     * Creates a new date by adding the specified number of years to the base
     * date.
     *
     * @param years the number of years to add (can be negative).
     * @return A new date.
     */
    public DayDate plusYears(int years) {
        int baseY = this.getYear();
        Month baseM = this.getMonth();
        int baseD = this.getDayOfMonth();

        int targetY = baseY + years;
        int targetD = Math.min(baseD, baseM.lastDayOfMonth(targetY));

        return DayDateFactory.makeDate(targetD, baseM, targetY);
    }

    /**
     * Returns the latest date that falls on the specified day-of-the-week and
     * is BEFORE the base date.
     *
     * @param targetWeekday a code for the target day-of-the-week.
     * @return the latest date that falls on the specified day-of-the-week and
     * is BEFORE the base date.
     */
    public DayDate getPreviousDayOfWeek(DayOfWeek targetWeekday) {
        int adjust;
        int baseDOW = this.getDayOfWeek().index;
        if (baseDOW > targetWeekday.index) {
            adjust = Math.min(0, targetWeekday.index - baseDOW);
        } else {
            adjust = -7 + Math.max(0, targetWeekday.index - baseDOW);
        }

        return this.plusDays(adjust);
    }

    /**
     * Returns the earliest date that falls on the specified day-of-the-week
     * and is AFTER the base date.
     *
     * @param targetWeekday a code for the target day-of-the-week.
     * @return the earliest date that falls on the specified day-of-the-week
     * and is AFTER the base date.
     */
    public DayDate getFollowingDayOfWeek(DayOfWeek targetWeekday) {
        final int adjust;
        final int baseDOW = this.getDayOfWeek().index;
        if (baseDOW >= targetWeekday.index) {
            adjust = 7 + Math.min(0, targetWeekday.index - baseDOW);
        } else {
            adjust = Math.max(0, targetWeekday.index - baseDOW);
        }

        return this.plusDays(adjust);
    }

    /**
     * Returns the date that falls on the specified day-of-the-week and is
     * CLOSEST to the base date.
     *
     * @param targetDOW a code for the target day-of-the-week.
     * @return the date that falls on the specified day-of-the-week and is
     * CLOSEST to the base date.
     */
    public DayDate getNearestDayOfWeek(DayOfWeek targetDOW) {
        int adjust = -Math.abs(targetDOW.index - this.getDayOfWeek().index);
        if (adjust >= 4) {
            adjust = 7 - adjust;
        }
        if (adjust <= -4) {
            adjust = 7 + adjust;
        }
        return this.plusDays(adjust);
    }

    /**
     * Rolls the date forward to the last day of the month.
     *
     * @param base the base date.
     * @return a new serial date.
     */
    public DayDate getEndOfCurrentMonth(final DayDate base) {
        return DayDateFactory.makeDate(
                base.getMonth().lastDayOfMonth(base.getYear()),
                base.getMonth(), base.getYear());
    }

    /**
     * Returns the serial number for the date, where 1 January 1900 = 2 (this
     * corresponds, almost, to the numbering system used in Microsoft Excel for
     * Windows and Lotus 1-2-3).
     *
     * @return the serial number for the date.
     */
    public abstract int getOrdinalDay();

    /**
     * Converts the date to a string.
     *
     * @return a string representation of the date.
     */
    public String toString() {
        return String.format("%d-%s-%d", getDayOfMonth(), getMonth(), getYear());
    }

    /**
     * Returns a <code>java.util.Date</code> equivalent to this date.
     *
     * @return The date.
     */
    public Date toDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getYear(), getMonth().index - 1, getDayOfMonth(), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * Returns the year (assume a valid range of 1900 to 9999).
     *
     * @return the year.
     */
    public abstract int getYear();

    /**
     * Returns the month (January = 1, February = 2, March = 3).
     *
     * @return the month of the year.
     */
    public abstract Month getMonth();

    /**
     * Returns the day of the month.
     *
     * @return the day of the month.
     */
    public abstract int getDayOfMonth();

    /**
     * Returns the day of the week.
     *
     * @return the day of the week.
     */
    public abstract DayOfWeek getDayOfWeek();

    /**
     * Returns the difference (in days) between this date and the specified
     * 'other' date.
     * <p>
     * The result is positive if this date is after the 'other' date and
     * negative if it is before the 'other' date.
     *
     * @param other the date being compared to.
     * @return the difference between this and the other date.
     */
    public abstract int compare(DayDate other);

    /**
     * Returns true if this SerialDate represents the same date as the
     * specified SerialDate.
     *
     * @param other the date being compared to.
     * @return <code>true</code> if this SerialDate represents the same date as
     * the specified SerialDate.
     */
    public abstract boolean isOn(DayDate other);

    /**
     * Returns true if this SerialDate represents an earlier date compared to
     * the specified SerialDate.
     *
     * @param other The date being compared to.
     * @return <code>true</code> if this SerialDate represents an earlier date
     * compared to the specified SerialDate.
     */
    public abstract boolean isBefore(DayDate other);

    /**
     * Returns true if this SerialDate represents the same date as the
     * specified SerialDate.
     *
     * @param other the date being compared to.
     * @return <code>true</code> if this SerialDate represents the same date
     * as the specified SerialDate.
     */
    public abstract boolean isOnOrBefore(DayDate other);

    /**
     * Returns true if this SerialDate represents the same date as the
     * specified SerialDate.
     *
     * @param other the date being compared to.
     * @return <code>true</code> if this SerialDate represents the same date
     * as the specified SerialDate.
     */
    public abstract boolean isAfter(DayDate other);

    /**
     * Returns true if this SerialDate represents the same date as the
     * specified SerialDate.
     *
     * @param other the date being compared to.
     * @return <code>true</code> if this SerialDate represents the same date
     * as the specified SerialDate.
     */
    public abstract boolean isOnOrAfter(DayDate other);

    /**
     * Returns <code>true</code> if this {@link DayDate} is within the
     * specified range (INCLUSIVE).  The date order of d1 and d2 is not
     * important.
     *
     * @param d1 a boundary date for the range.
     * @param d2 the other boundary date for the range.
     * @return A boolean.
     */
    public abstract boolean isInRange(DayDate d1, DayDate d2);

    /**
     * Returns <code>true</code> if this {@link DayDate} is within the
     * specified range (caller specifies whether or not the end-points are
     * included).  The date order of d1 and d2 is not important.
     *
     * @param d1      a boundary date for the range.
     * @param d2      the other boundary date for the range.
     * @param include a code that controls whether or not the start and end
     *                dates are included in the range.
     * @return A boolean.
     */
    public abstract boolean isInRange(DayDate d1, DayDate d2,
                                      int include);
}
