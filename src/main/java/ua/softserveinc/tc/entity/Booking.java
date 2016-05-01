package ua.softserveinc.tc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by TARAS on 30.04.2016.
 */
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book", nullable = false)
    private Long idBook;

    @Column(name = "id_child", nullable = false)
    private Long idChild;

    @Column(name = "id_room", nullable = false)
    private Long idRoom;

    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Column(name = "booking_start_time", nullable = false)
    private Date bookingStartTime;

    @Column(name = "booking_end_time", nullable = false)
    private Date bookingEndTime;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_canceled", nullable = false)
    private boolean isCanceled;


    public Booking() {
    }

    public Booking(Long idChild, Long idRoom, Long idUser, Date bookingStartTime, Date bookingEndTime,
                   String comment, boolean isCanceled) {
        this.idChild = idChild;
        this.idRoom = idRoom;
        this.idUser = idUser;
        this.bookingStartTime = bookingStartTime;
        this.bookingEndTime = bookingEndTime;
        this.comment = comment;
        this.isCanceled = isCanceled;
    }


    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public Long getIdChild() {
        return idChild;
    }

    public void setIdChild(Long idChild) {
        this.idChild = idChild;
    }

    public Long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Long idRoom) {
        this.idRoom = idRoom;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
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

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }
}
