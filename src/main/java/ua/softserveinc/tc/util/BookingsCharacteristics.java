package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.Child;

import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * The immutable class that represent the characteristics of the made Bookings.
 * The characteristics are: list of the rooms, list of the children, array of the
 * dates of the bookings, list of the id of the bookings, list of the id of the
 * recurrent bookings. If you don't want have all of the characteristics, you just
 * not set that values. In that case the appropriate values will be assigned with
 * empty list or array with null values.
 * To create object of this class, you should first create object of inner class
 * Builder, then set all characteristics and finally invoke build method. The
 * class is thread safe.
 * <p>
 * Created by Sviatoslav Hryb on 02-Jan-17.
 */
public class BookingsCharacteristics {

    private final List<Room> listOfRoomOfBookings;
    private final List<Child> childrenListOfBookings;
    private final Date[] datesOfBookings;
    private final List<Long> listOfIdOfBookings;
    private final List<Long> listOfIdOfRecurrentBookings;

    /**
     * Returns the list of the rooms.
     *
     * @return list of the rooms
     */
    public List<Room> getListOfRoomOfBookings() {

        return listOfRoomOfBookings;
    }

    /**
     * Returns the list of the children.
     *
     * @return the list of the children
     */
    public List<Child> getChildrenListOfBookings() {

        return childrenListOfBookings;
    }

    /**
     * Returns the array of the children
     *
     * @return the array of the children
     */
    public Date[] getDatesOfBookings() {

        return datesOfBookings;
    }

    /**
     * Returns the start date of the bookings
     *
     * @return the start date of the bookings
     */
    public Date getStartDateOfBookings() {

        return datesOfBookings[0];
    }

    /**
     * Returns the end date of the bookings
     *
     * @return the end date of the bookings
     */
    public Date getEndDateOfBookings() {

        return datesOfBookings[1];
    }

    /**
     * Returns the list of the id of the bookings
     *
     * @return the list of the id of the bookings
     */
    public List<Long> getListOfIdOfBookings() {

        return listOfIdOfBookings;
    }

    /**
     * Returns the list of the id of the recurrent bookings
     *
     * @return the list of the id of the bookings
     */
    public List<Long> getListOfIdOfRecurrentBookings() {

        return listOfIdOfRecurrentBookings;
    }

    public boolean isCorrectFotDuplicateCheck() {

        return listOfIdOfRecurrentBookings.stream().noneMatch(recurrentId -> recurrentId == null)
                && Arrays.stream(datesOfBookings).noneMatch(date -> date == null)
                && listOfIdOfBookings.stream().noneMatch(idBooking -> idBooking == null)
                && childrenListOfBookings.stream().noneMatch(child -> child == null)
                && listOfRoomOfBookings.stream().noneMatch(room -> room == null)
                && !childrenListOfBookings.isEmpty();

    }

    /**
     * The inner static class by which we can create yje object of
     * BookingsCharacteristics class
     */
    public static class Builder {

        private List<Room> listOfRoomOfBookings;
        private List<Child> childrenListOfBookings;
        private Date[] datesOfBookings;
        private List<Long> listOfIdOfBookings;
        private List<Long> listOfIdOfRecurrentBookings;

        /**
         * Sets the list of the rooms.
         *
         * @param rooms the list of the rooms.
         * @return builder objects
         */
        public Builder setRoomOfBookings(List<Room> rooms) {
            listOfRoomOfBookings = rooms;

            return this;
        }

        /**
         * Sets the list of the children.
         *
         * @param children the list of the children.
         * @return builder objects
         */
        public Builder setChildrenListOfBookings(List<Child> children) {
            childrenListOfBookings = children;

            return this;
        }

        /**
         * Sets the array of the children. Start date should be on the index 0
         * and end date should be on the index 1 of the given array of dates.
         *
         * @param dates the array of the children
         * @return builder objects
         */
        public Builder setDatesOfBookings(Date[] dates) {
            datesOfBookings = dates;

            return this;
        }

        /**
         * Sets the list of the id of the bookings
         *
         * @param idBookings the list of the id of the bookings
         * @return builder objects
         */
        public Builder setListOfIdOfBookings(List<Long> idBookings) {
            listOfIdOfBookings = idBookings;

            return this;
        }

        /**
         * Sets the list of the id of the recurrent bookings
         *
         * @param idRecurrent the list of the id of the recurrent bookings
         * @return builder objects
         */
        public Builder setListOfIdOfRecurrentBookings(List<Long> idRecurrent) {
            listOfIdOfRecurrentBookings = idRecurrent;

            return this;
        }

        /**
         * Creates the objects of BookingsCharacteristics class
         *
         * @return the objects of BookingsCharacteristics class
         */
        public BookingsCharacteristics build() {

            if (listOfRoomOfBookings == null)
                listOfRoomOfBookings = new ArrayList<>();
            if (childrenListOfBookings == null)
                childrenListOfBookings = new ArrayList<>();
            if (datesOfBookings == null)
                datesOfBookings = new Date[]{null, null};
            if (listOfIdOfBookings == null)
                listOfIdOfBookings = new ArrayList<>();
            if (listOfIdOfRecurrentBookings == null)
                listOfIdOfRecurrentBookings = new ArrayList<>();

            return new BookingsCharacteristics(this);
        }

    }

    private BookingsCharacteristics(Builder builder) {

        listOfRoomOfBookings = builder.listOfRoomOfBookings;
        childrenListOfBookings = builder.childrenListOfBookings;
        datesOfBookings = builder.datesOfBookings;
        listOfIdOfBookings = builder.listOfIdOfBookings;
        listOfIdOfRecurrentBookings = builder.listOfIdOfRecurrentBookings;
    }
}
