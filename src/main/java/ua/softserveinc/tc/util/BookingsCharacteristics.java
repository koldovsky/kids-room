package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.entity.BookingState;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.Collections;


/**
 * The class that represent the characteristics of the made Bookings.
 * The characteristics are: list of the rooms, list of the children, list of users,
 * list of bookings states, array of the dates of the bookings, list of the id of
 * the bookings, list of the id of the recurrent bookings. If you don't want have
 * all of the characteristics, you just not set that values. In that case the
 * appropriate values will be assigned with empty list or array with null values.
 * To create object of this class, you should first create object of inner class
 * Builder, then set all characteristics and finally invoke build method.
 * <strong></>The class is not thread safe!</strong>
 * <p>
 * Created by Sviatoslav Hryb on 02-Jan-17.
 */
public class BookingsCharacteristics {

    private final List<Room> rooms;
    private final List<Child> children;
    private final List<User> users;
    private final List<BookingState> bookingsStates;
    private final Date[] dates;
    private final List<Long> idsOfBookings;
    private final List<Long> recurrentIdsOfBookings;

    /**
     * Returns the list of the rooms.
     *
     * @return list of the rooms
     */
    public List<Room> getRooms() {

        return new ArrayList<>(rooms);
    }

    /**
     * Returns the list of the children.
     *
     * @return the list of the children
     */
    public List<Child> getChildren() {

        return new ArrayList<>(children);
    }

    /**
     * Returns the list of the users.
     *
     * @return list of the users
     */
    public List<User> getUsers() {

        return new ArrayList<>(users);
    }

    /**
     * Returns the list of the BookingsState.
     *
     * @return list of the bookings states
     */
    public List<BookingState> getBookingsStates() {

        return new ArrayList<>(bookingsStates);
    }

    /**
     * Returns the array of the children
     *
     * @return the array of the children
     */
    public Date[] getDates() {

        return new Date[] {dates[0], dates[1]};
    }

    /**
     * Returns the start date of the bookings
     *
     * @return the start date of the bookings
     */
    public Date getStartDateOfBookings() {

        return dates[0];
    }

    /**
     * Returns the end date of the bookings
     *
     * @return the end date of the bookings
     */
    public Date getEndDateOfBookings() {

        return dates[1];
    }

    /**
     * Returns the list of the id of the bookings
     *
     * @return the list of the id of the bookings
     */
    public List<Long> getIdsOfBookings() {

        return new ArrayList<>(idsOfBookings);
    }

    /**
     * Returns the list of the id of the recurrent bookings
     *
     * @return the list of the id of the bookings
     */
    public List<Long> getRecurrentIdsOfBookings() {

        return new ArrayList<>(recurrentIdsOfBookings);
    }

    /**
     * Checks if this object has set rooms
     *
     * @return true if rooms are set, otherwise - false
     */
    public boolean hasSetRooms() {

        return !rooms.isEmpty();
    }

    /**
     * Checks if this object has set children
     *
     * @return true if children are set, otherwise - false
     */
    public boolean hasSetChildren() {

        return !children.isEmpty();
    }

    /**
     * Checks if this object has set users
     *
     * @return true if users are set, otherwise - false
     */
    public boolean hasSetUsers() {

        return !users.isEmpty();
    }

    /**
     * Checks if this object has set bookings states
     *
     * @return true if bookings states are set, otherwise - false
     */
    public boolean hasSetBookingsStates() {

        return !bookingsStates.isEmpty();
    }

    /**
     * Checks if this object has set dates
     *
     * @return true if dates are set, otherwise - false
     */
    public boolean hasSetDates() {

        return getStartDateOfBookings() != null && getEndDateOfBookings() != null;
    }

    /**
     * Checks if this object has set only start date
     *
     * @return true if only start date are set, otherwise - false
     */
    public boolean hasSetOnlyStartDate() {

        return getStartDateOfBookings() != null && getEndDateOfBookings() == null;
    }

    /**
     * Checks if this object has set only end date
     *
     * @return true if only end date are set, otherwise - false
     */
    public boolean hasSetOnlyEndDate() {

        return getStartDateOfBookings() == null && getEndDateOfBookings() != null;
    }

    /**
     * Checks if this object has set ids of bookings
     *
     * @return true if ids of bookings are set, otherwise - false
     */
    public boolean hasSetIdsOfBookings() {

        return !idsOfBookings.isEmpty();
    }

    /**
     * Checks if this object has set recurrent ids of bookings
     *
     * @return true if recurrent ids are set, otherwise - false
     */
    public boolean hasSetRecurrentIdsOfBookings() {

        return !recurrentIdsOfBookings.isEmpty();
    }

    /**
     * Checks if this object is correct for duplicate bookings checks
     *
     * @return true is this object os correct, otherwise - false.
     */
    public boolean isCorrectFotDuplicateCheck() {

        return recurrentIdsOfBookings.stream().noneMatch(Objects::isNull)
                && Arrays.stream(dates).noneMatch(Objects::isNull)
                && idsOfBookings.stream().noneMatch(Objects::isNull)
                && children.stream().noneMatch(Objects::isNull)
                && rooms.stream().noneMatch(Objects::isNull)
                && !children.isEmpty();

    }

    /**
     * The inner static class by which we can create yje object of
     * BookingsCharacteristics class
     */
    public static class Builder {

        private List<Room> rooms;
        private List<Child> children;
        private List<User> users;
        private List<BookingState> bookingsStates;
        private Date[] dates;
        private List<Long> idsOfBookings;
        private List<Long> recurrentIdsOfBookings;

        /**
         * Sets the list of the rooms.
         *
         * @param rooms the list of the rooms.
         * @return builder objects
         */
        public Builder setRooms(List<Room> rooms) {
            this.rooms = rooms;

            return this;
        }

        /**
         * Sets the list of the children.
         *
         * @param children the list of the children.
         * @return builder objects
         */
        public Builder setChildren(List<Child> children) {
            this.children = children;

            return this;
        }

        /**
         * Sets the list of the users.
         *
         * @param users the list of the users.
         * @return builder objects
         */
        public Builder setUsers(List<User> users) {
            this.users = users;

            return this;
        }

        /**
         * Sets the list of the bookings states.
         *
         * @param bookingsStates the list of the bookings states.
         * @return builder objects
         */
        public Builder setBookingsStates(List<BookingState> bookingsStates) {
            this.bookingsStates = bookingsStates;

            return this;
        }

        /**
         * Sets the array of the children. Start date should be on the index 0
         * and end date should be on the index 1 of the given array of dates.
         *
         * @param dates the array of the children
         * @return builder objects
         */
        public Builder setDates(Date[] dates) {
            this.dates = dates;

            return this;
        }

        /**
         * Sets the list of the id of the bookings
         *
         * @param idsOfBookings the list of the id of the bookings
         * @return builder objects
         */
        public Builder setIdsOfBookings(List<Long> idsOfBookings) {
            this.idsOfBookings = idsOfBookings;

            return this;
        }

        /**
         * Sets the list of the id of the recurrent bookings
         *
         * @param recurrentIdsOfBookings the list of the id of the recurrent bookings
         * @return builder objects
         */
        public Builder setRecurrentIdsOfBookings(List<Long> recurrentIdsOfBookings) {
            this.recurrentIdsOfBookings = recurrentIdsOfBookings;

            return this;
        }

        /**
         * Creates the objects of BookingsCharacteristics class
         *
         * @return the objects of BookingsCharacteristics class
         */
        public BookingsCharacteristics build() {

            rooms = (rooms == null) ? Collections.emptyList() : rooms;
            children = (children == null) ? Collections.emptyList() : children;
            users = (users == null) ? Collections.emptyList() : users;
            bookingsStates = (bookingsStates == null) ? Collections.emptyList() : bookingsStates;
            dates = (dates == null) ? new Date[] {null, null} : dates;
            idsOfBookings = (idsOfBookings == null) ? Collections.emptyList() : idsOfBookings;
            recurrentIdsOfBookings = (recurrentIdsOfBookings == null) ?
                    Collections.emptyList() : recurrentIdsOfBookings;

            return new BookingsCharacteristics(this);
        }

    }

    private BookingsCharacteristics(Builder builder) {

        rooms = builder.rooms;
        children = builder.children;
        dates = builder.dates;
        idsOfBookings = builder.idsOfBookings;
        recurrentIdsOfBookings = builder.recurrentIdsOfBookings;
        users = builder.users;
        bookingsStates = builder.bookingsStates;
    }
}
