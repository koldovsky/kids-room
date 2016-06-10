package ua.softserveinc.tc.entity;

import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.constants.model.TokenConst;

import javax.persistence.*;


/**
 * Created by Chak on 17.05.2016.
 */
@Entity
@Table(name = TokenConst.TABLE_NAME_TOKEN)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = TokenConst.ID_TOKEN, nullable = false)
    private Long id;

    @Column(name = TokenConst.TOKEN, nullable = false)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = UserConstants.Entity.ID_USER)
    private User user;


    public Token() {
        super();
    }

    public Token(String token, User user) {
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
