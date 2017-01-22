package ua.softserveinc.tc.util;

/**
 * Two tuple that represent two object: Long that represent date and Boolean that
 * is true if date is start, otherwise - false. The class implements Comparable interface
 * that sort all elements by Long objects in ascending order. If two Long object are compared
 * to 0 then addition sort by second object is making. In that case the lowest object is object
 * its isStart is false.
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
        int result = getDateLong().compareTo(tuple.getDateLong());

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
