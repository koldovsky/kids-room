package ua.softserveinc.tc.entity;

import javax.persistence.*;

import static ua.softserveinc.tc.entity.ColumnConstants.UserConst.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name=ID_USER)
    private Long id;

    @Column(name=FIRST_NAME)
    private String firstName;

    @Column(name=LAST_NAME)
    private String lastName;

    @Column(name=EMAIL)
    private String email;

    @Column(name=PASSWORD)
    private String password;

    @Column(name=PHONE)
    private String phoneNumber;

    @Column(name = ROLE)
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
