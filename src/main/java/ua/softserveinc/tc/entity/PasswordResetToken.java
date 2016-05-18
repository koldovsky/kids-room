package ua.softserveinc.tc.entity;

import ua.softserveinc.tc.constants.ColumnConstants.UserConst;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Chak on 18.05.2016.
 */
@Entity
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = UserConst.ID_USER)
    private User user;

    private Date expiryDate;
}