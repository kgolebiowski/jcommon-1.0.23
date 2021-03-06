/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
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
 * --------------------------
 * RelativeDayOfWeekRule.java
 * --------------------------
 * (C) Copyright 2000-2003, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: RelativeDayOfWeekRule.java,v 1.6 2005/11/16 15:58:40 taqua Exp $
 *
 * Changes (from 26-Oct-2001)
 * --------------------------
 * 26-Oct-2001 : Changed package to com.jrefinery.date.*;
 * 03-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 *
 */

package org.jfree.date;

import org.jfree.date.units.WeekdayRange;
import org.jfree.date.units.DayOfWeek;

/**
 * An annual date rule that returns a date for each year based on (a) a
 * reference rule; (b) a day of the week; and (c) a selection parameter
 * (SerialDate.PRECEDING, SerialDate.NEAREST, SerialDate.FOLLOWING).
 * <P>
 * For example, Good Friday can be specified as 'the Friday PRECEDING Easter 
 * Sunday'.
 *
 * @author David Gilbert
 */
public class RelativeDayOfWeekRule extends AnnualDateRule {

    /** A reference to the annual date rule on which this rule is based. */
    private AnnualDateRule subrule;

    private DayOfWeek dayOfWeek;

    private WeekdayRange weekdayRange;

    /**
     * Default constructor - builds a rule for the Monday following 1 January.
     */
    public RelativeDayOfWeekRule() {
        this(new DayAndMonthRule(), DayOfWeek.MONDAY, WeekdayRange.FOLLOWING);
    }

    /**
     * Standard constructor - builds rule based on the supplied sub-rule.
     *
     * @param subrule  the rule that determines the reference date.
     * @param dayOfWeek  the day-of-the-week dateRelation to the reference date.
     * @param relation  indicates *which* day-of-the-week (preceding, nearest
     *                  or following).
     */
    public RelativeDayOfWeekRule(AnnualDateRule subrule, DayOfWeek dayOfWeek, WeekdayRange relation) {
        this.subrule = subrule;
        this.dayOfWeek = dayOfWeek;
        this.weekdayRange = relation;
    }

    /**
     * Returns the sub-rule (also called the reference rule).
     *
     * @return The annual date rule that determines the reference date for this 
     *         rule.
     */
    public AnnualDateRule getSubrule() {
        return this.subrule;
    }

    /**
     * Sets the sub-rule.
     *
     * @param subrule  the annual date rule that determines the reference date 
     *                 for this rule.
     */
    public void setSubrule(final AnnualDateRule subrule) {
        this.subrule = subrule;
    }

    /**
     * Returns the day-of-the-week for this rule.
     *
     * @return the day-of-the-week for this rule.
     */
    public DayOfWeek getDayOfWeek() {
        return this.dayOfWeek;
    }

    /**
     * Sets the day-of-the-week for this rule.
     *
     * @param dayOfWeek  the day-of-the-week (SerialDate.MONDAY, 
     *                   SerialDate.TUESDAY, and so on).
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Returns the 'dateRelation' attribute, that determines *which*
     * day-of-the-week we are interested in (SerialDate.PRECEDING, 
     * SerialDate.NEAREST or SerialDate.FOLLOWING).
     *
     * @return The 'dateRelation' attribute.
     */
    public WeekdayRange getWeekdayRange() {
        return this.weekdayRange;
    }

    /**
     * Sets the 'dateRelation' attribute (SerialDate.PRECEDING, SerialDate.NEAREST,
     * SerialDate.FOLLOWING).
     *
     * @param weekdayRange  determines *which* day-of-the-week is selected by this
     *                  rule.
     */
    public void setWeekdayRange(WeekdayRange weekdayRange) {
        this.weekdayRange = weekdayRange;
    }

    /**
     * Creates a clone of this rule.
     *
     * @return a clone of this rule.
     *
     * @throws CloneNotSupportedException this should never happen.
     */
    public Object clone() throws CloneNotSupportedException {
        final RelativeDayOfWeekRule duplicate 
            = (RelativeDayOfWeekRule) super.clone();
        duplicate.subrule = (AnnualDateRule) duplicate.getSubrule().clone();
        return duplicate;
    }

    /**
     * Returns the date generated by this rule, for the specified year.
     *
     * @param year  the year (1900 &lt;= year &lt;= 9999).
     *
     * @return The date generated by the rule for the given year (possibly 
     *         <code>null</code>).
     */
    public DayDate getDate(final int year) {

        // check argument...
        if ((year < SpreadsheetDate.MINIMUM_YEAR_SUPPORTED)
            || (year > SpreadsheetDate.MAXIMUM_YEAR_SUPPORTED)) {
            throw new IllegalArgumentException(
                "RelativeDayOfWeekRule.getDate(): year outside valid range.");
        }

        // calculate the date...
        DayDate result = null;
        DayDate base = subrule.getDate(year);

        if (base != null) {
            switch (weekdayRange) {
                case PRECEDING:
                    result = base.getPreviousDayOfWeek(this.dayOfWeek);
                    break;
                case NEAREST:
                    result = base.getNearestDayOfWeek(this.dayOfWeek);
                    break;
                case FOLLOWING:
                    result = base.getFollowingDayOfWeek(this.dayOfWeek);
                    break;
                default:
                    break;
            }
        }
        return result;

    }

}
