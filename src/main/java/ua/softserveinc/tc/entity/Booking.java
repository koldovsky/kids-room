package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.constants.ChildConstants;
import ua.softserveinc.tc.constants.RoomConstants;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.dto.BookingDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

import static ua.softserveinc.tc.util.DateUtil.toHoursAndMinutes;



@Entity
@Table(name = BookingConstants.DB.TABLE_NAME_BOOKING)
public class Booking {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = BookingConstants.DB.ID_BOOK, nullable = false)
    private Long idBook;

    @ManyToOne(optional = false)//(cascade = CascadeType.ALL)
    @JoinColumn(name = ChildConstants.ID_CHILD )
    @Embedded
    private Child child;

    @ManyToOne(optional = false)//(fetch = FetchType.LAZY)
    @JoinColumn(name = RoomConstants.ID_ROOM)
    private Room room;

    @ManyToOne(optional = false)
    @JoinColumn(name = UserConstants.Entity.ID_USER)
    private User user;

    @Column(name = BookingConstants.DB.BOOKING_START_TIME, nullable = false)
    private Date bookingStartTime;

    @Column(name = BookingConstants.DB.BOOKING_END_TIME, nullable = false)
    @NotNull
    private Date bookingEndTime;

    @Column(name = BookingConstants.DB.COMMENT)
    private String comment;


    @Column(name = BookingConstants.DB.BOOKING_STATE)
    @Enumerated(EnumType.ORDINAL)
    private BookingState bookingState;

    @NotNull
    @Column(name = BookingConstants.DB.DURATION, columnDefinition = "bigint default 0")
    private Long duration = 0L;

    @NotNull
    @Column(name = BookingConstants.DB.SUM, columnDefinition = "bigint default 0")
    private Long sum = 0L;

    @Column(name = BookingConstants.DB.ID_RECURRENT, columnDefinition = "bigint default NULL")
    private Long recurrentId;

    @Column(name = "discounts")
    private String discounts;

    @Column(name = "abonnements")
    private String abonnements;

    public Booking() {
    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(Date bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public Date getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(Date bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public BookingState getBookingState() {
        return bookingState;
    }

    public void setBookingState(BookingState bookingState) {
        this.bookingState = bookingState;
    }

    public String formatDuration() {
        return toHoursAndMinutes(duration);
    }

    public BookingDto getDto(){
        return new BookingDto(this);
    }

    public Long getRecurrentId() {
        return recurrentId;
    }

    public void setRecurrentId(Long recurrentId) {
        this.recurrentId = recurrentId;
    }

    public String getDiscounts() {
        return discounts;
    }

    public void setDiscounts(String discounts) {
        this.discounts = discounts;
    }

    public String getAbonnements() {
        return abonnements;
    }

    public void setAbonnements(String abonnements) {
        this.abonnements = abonnements;
    }

    public void setAbonnements(LocalTime abonnements) {
        if (!abonnements.equals(LocalTime.MIN)) {
            this.abonnements = abonnements.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(idBook, booking.idBook) &&
                Objects.equals(child, booking.child) &&
                Objects.equals(room, booking.room) &&
                Objects.equals(user, booking.user) &&
                Objects.equals(bookingStartTime, booking.bookingStartTime) &&
                Objects.equals(bookingEndTime, booking.bookingEndTime) &&
                Objects.equals(comment, booking.comment) &&
                bookingState == booking.bookingState &&
                Objects.equals(duration, booking.duration) &&
                Objects.equals(sum, booking.sum) &&
                Objects.equals(recurrentId, booking.recurrentId) &&
                Objects.equals(discounts, booking.discounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBook, child, room, user, bookingStartTime, bookingEndTime, comment, bookingState, duration, sum, recurrentId, discounts);
    }
}
