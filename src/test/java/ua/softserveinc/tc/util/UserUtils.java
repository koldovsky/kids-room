package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;

import java.util.Arrays;
import java.util.List;

public class UserUtils {

    public static List<User> getListOfUser() {

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Lilian");
        user1.setLastName("Anderson");
        user1.setEmail("lilian@com.ua");
        user1.setRole(Role.USER);
        user1.setPhoneNumber("+380993456543");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Maria");
        user2.setLastName("Jons");
        user2.setEmail("maria@com.ua");
        user2.setRole(Role.USER);
        user2.setPhoneNumber("+38099349394");

        User user3 = new User();
        user3.setId(3L);
        user3.setFirstName("Vika");
        user3.setLastName("Po");
        user3.setEmail("vika@com.ua");
        user3.setRole(Role.USER);
        user3.setPhoneNumber("+380999245324");

        User user4 = createUser(3L, "Alan", "Bom", "user@softserveinc.com",
                "$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02", Role.MANAGER,
                "+380987654321", true, true);

        User user5 = createUser(1L, "Alan", "Bom", "user@softserveinc.com",
                "$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02", Role.MANAGER,
                "+380987654321", true, true);

        return Arrays.asList(user1, user2, user3, user4, user5);
    }

    public static User createUser(Long id, String firstName, String lastName, String email, String password,
                                  Role role, String phoneNumber, boolean confirmed, boolean active) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setPhoneNumber(phoneNumber);
        user.setConfirmed(confirmed);
        user.setActive(active);

        return user;
    }

}
