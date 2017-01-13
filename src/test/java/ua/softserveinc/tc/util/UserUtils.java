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

        User user4 = new User();
        user4.setId(3L);
        user4.setFirstName("Alan");
        user4.setLastName("Bom");
        user4.setEmail("user@softserveinc.com");
        user4.setPassword("$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02");
        user4.setRole(Role.MANAGER);
        user4.setPhoneNumber("+380987654321");
        user4.setConfirmed(true);
        user4.setActive(true);

        User user5 = new User();
        user5.setId(1L);
        user5.setFirstName("Alan");
        user5.setLastName("Bom");
        user5.setEmail("user@softserveinc.com");
        user5.setPassword("$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02");
        user5.setRole(Role.USER);
        user5.setPhoneNumber("+380987654321");
        user5.setConfirmed(true);
        user5.setActive(true);

        return Arrays.asList(user1, user2, user3, user4, user5);
    }

    public static List<Long> getListOfIds3() {
        return Arrays.asList(1L, 2L, 3L);
    }
}
