package ua.softserveinc.tc.entity;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "persistent_logins")
public class RememberMeToken implements Serializable {

    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "series")
    private String series;

    @Column(name = "token")
    private String token;

    @Column(name = "last_used")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public RememberMeToken() {
    }

    public RememberMeToken(PersistentRememberMeToken token) {
        this.username = token.getUsername();
        this.series = token.getSeries();
        this.token = token.getTokenValue();
        this.date = token.getDate();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}