package ua.softserveinc.tc.util;

import java.util.Date;

/**
 * Two tuple that represent two object: Date that represent date and Boolean that
 * is true if date is start, otherwise - false. The class implements Comparable interface
 * that sort all elements by Date objects in ascending order. If two Date object are compared
 * to 0 then addition sort by second object is making. In that case the lowest object is object
 * its isStart is false.
 */
public class DateTwoTuple extends TwoTuple <Date,Boolean> implements Comparable<DateTwoTuple> {

    /**
     * To create object of this class we must use this constructor.
     *
     * @param date given date object
     * @param isStart true is given date is start, otherwise false
     */
    public DateTwoTuple(Date date, Boolean isStart) {

        super(date, isStart);
    }

    /**
     * Returns contained date object
     *
     * @return the contained date
     */
    public Date getDate() {

        return getFirst();
    }

    /**
     * Returns contained Boolean object
     *
     * @return the contained Boolean object
     */
    public Boolean isStart() {

        return getSecond();
    }

    /**
     * Comparing by first objects, if they are comparing to 0, then addition
     * comparing is make by second object. In that case the lowest object is object
     * its isStart is false.
     *
     * @param tuple the given DteTwoTuple object
     * @return 1 if this object is bigger then given, -1 if this object is lower then
     * given, 0 if this nad given objects are equal in sense of comparing.
     */
    @Override
    public int compareTo (DateTwoTuple tuple) {
        int result = getDate().compareTo(tuple.getDate());

        if (result == 0) {
            if (!isStart() && tuple.isStart()) {
                result = -1;
            } else if (isStart() && !tuple.isStart()) {
                result = 1;
            }
        }

        return result;
    }
}
