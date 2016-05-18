package ua.softserveinc.tc.entity;

import ua.softserveinc.tc.constants.ColumnConstants.UserConst;

import javax.persistence.*;


/**
 * Created by Chak on 17.05.2016.
 */
@Entity
public class VerificationToken {
    private static final int EXPIRATION = 24*60;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = UserConst.ID_USER)
    private User user;


    public VerificationToken() {
        super();
    }

    public VerificationToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
