package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.BookingConstant;
import ua.softserveinc.tc.constants.ColumnConstants.ChildConst;
import ua.softserveinc.tc.constants.ColumnConstants.RoomConst;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.util.DateUtilImpl;

import javax.persistence.*;
import java.util.Date;


/**
 * Created by TARAS on 30.04.2016.
 */
@Entity
@Table(name = BookingConstant.DB.TABLE_NAME_BOOKING)
public class Booking
{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = BookingConstant.DB.ID_BOOK, nullable = false)
    private Long idBook;

    @ManyToOne(optional = false)//(cascade = CascadeType.ALL)
    @JoinColumn(name = ChildConst.ID_CHILD)
    private Child idChild;

    @ManyToOne(optional = false)//(fetch = FetchType.LAZY)
    @JoinColumn(name = RoomConst.ID_ROOM)
    private Room idRoom;

    @ManyToOne(optional = false)
    @JoinColumn(name = UserConstants.ID_USER)
    private User idUser;

    @Column(name = BookingConstant.DB.BOOKING_START_TIME, nullable = false)
    private Date bookingStartTime;

    @Column(name = BookingConstant.DB.BOOKING_END_TIME, nullable = false)
    private Date bookingEndTime;

    @Column(name = BookingConstant.DB.COMMENT)
    private String comment;


    @Column(name = BookingConstant.DB.BOOKING_STATE)
    @Enumerated(EnumType.ORDINAL)
    private BookingState bookingState;

    @Column(name = BookingConstant.DB.DURATION, columnDefinition = "bigint default 0")
    private Long duration;

    @Column(name = BookingConstant.DB.SUM, columnDefinition = "bigint default 0")
    private Long sum;

    @Transient
    private DateUtil dateUtil = new DateUtilImpl();

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public Child getIdChild() {
        return idChild;
    }

    public void setIdChild(Child idChild) {
        this.idChild = idChild;
    }

    public Room getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Room idRoom) {
        this.idRoom = idRoom;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
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

    public String formatDuration()
    {
        return dateUtil.toHoursAndMinutes(duration);
    }
}
