package ua.softserveinc.tc.entity;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import ua.softserveinc.tc.constants.UserConstants;
import ua.softserveinc.tc.constants.ValidationConstants;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NamedQueries({
        @NamedQuery(name = UserConstants.Entity.NQ_FIND_USER_BY_EMAIL, query = "from User WHERE email = :email")
})
@Entity
@Table(name = UserConstants.Entity.TABLE_NAME_USER)
@AnalyzerDef(name = "ngram",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = NGramFilterFactory.class,
                        params = {
                                @Parameter(name = "minGramSize", value = "3"),
                                @Parameter(name = "maxGramSize", value = "1024")
                        })
        })
@Embeddable
public class User implements Serializable {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = UserConstants.Entity.ID_USER, nullable = false)
    private Long id;

    @NotEmpty(message = ValidationConstants.NOT_EMPTY_MESSAGE)
    @Column(name = UserConstants.Entity.FIRST_NAME)
    @Field
    @Analyzer(definition = "ngram")
    private String firstName;

    @NotEmpty(message = ValidationConstants.NOT_EMPTY_MESSAGE)
    @Column(name = UserConstants.Entity.LAST_NAME)
    @Field
    @Analyzer(definition = "ngram")
    private String lastName;

    @NotEmpty
    @Email
    @Column(name = UserConstants.Entity.EMAIL, unique = true)
    @Field(store = Store.NO)
    @Analyzer(definition = "ngram")
    private String email;


    @Column(name = UserConstants.Entity.PASSWORD)
    private String password;


    private transient String confirm;

    @Column(name = UserConstants.Entity.CONFIRMED)
    private boolean confirmed;


    @Column(name = UserConstants.Entity.ACTIVE)
    private boolean active;

    @NotEmpty(message = ValidationConstants.NOT_EMPTY_MESSAGE)
    @Pattern(regexp = ValidationConstants.SIMPLY_PHONE_REGEX, message = ValidationConstants.NOT_VALID_MESSAGE)
    @Column(name = UserConstants.Entity.PHONE)
    @Field(store = Store.NO)
    @Analyzer(definition = "ngram")
    private String phoneNumber;

    @Column(name = UserConstants.Entity.ROLE)
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "parentId")
    private Set<Child> children;

    @ManyToMany(mappedBy = "managers")
    private List<Room> rooms = new ArrayList<>();

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public Set<Child> getChildren() {
        return children;
    }

    public void setChildren(Set<Child> children) {
        this.children = children;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Child> getEnabledChildren() {
        return children.stream()
                .filter(Child::isEnabled)
                .collect(Collectors.toList());
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
