package ua.softserveinc.tc.util;

/**
 * Two tuple that represent two object: Long that represent date and Boolean that
 * is true if date is start, otherwise - false. The class implements Comparable interface
 * that sort all elements by Long objects in ascending order.
 */
public class DateTwoTuple extends TwoTuple <Long,Boolean> implements Comparable<DateTwoTuple> {

    /**
     * To create object of this class we must use this constructor.
     *
     * @param date given date represented by Long object
     * @param isStart true is given date is start, otherwise false
     */
    public DateTwoTuple(Long date, Boolean isStart) {

        super(date, isStart);
    }

    /**
     * Returns contained long object
     *
     * @return the contained long date
     */
    public Long getDateLong() {

        return getFirst();
    }

    /**
     * Returns contained boolean object
     *
     * @return the contained boolean object
     */
    public Boolean isStart() {

        return getSecond();
    }

    @Override
    public int compareTo (DateTwoTuple tuple) {

        return getDateLong().compareTo(tuple.getDateLong());
    }
}
