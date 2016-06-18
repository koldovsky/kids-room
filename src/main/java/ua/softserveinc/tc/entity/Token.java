package ua.softserveinc.tc.entity;

import ua.softserveinc.tc.constants.TokenConstants;
import ua.softserveinc.tc.constants.UserConstants;

import javax.persistence.*;


/**
 * Created by Chak on 17.05.2016.
 */
@Entity
@Table(name = TokenConstants.TABLE_NAME_TOKEN)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = TokenConstants.ID_TOKEN, nullable = false)
    private Long id;

    @Column(name = TokenConstants.TOKEN, nullable = false)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = UserConstants.Entity.ID_USER)
    private User user;

    public Token() {
        //empty constructor for instantiating in controller
    }

    public Token(String token, User user) {
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
