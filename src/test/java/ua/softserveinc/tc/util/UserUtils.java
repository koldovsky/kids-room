package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by comp on 17.12.2016.
 */
public class UserUtils {

    public static List<User> getListOfUser()
    {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Lilian");
        user1.setLastName("Anderson");
        user1.setEmail("lilian@com.ua");
        user1.setRole(Role.USER);
        user1.setPhoneNumber("0993456543");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Maria");
        user2.setLastName("Jons");
        user2.setEmail("maria@com.ua");
        user2.setRole(Role.USER);
        user2.setPhoneNumber("099349394");

        User user3 = new User();
        user3.setId(3L);
        user3.setFirstName("Vika");
        user3.setLastName("Po");
        user3.setEmail("vika@com.ua");
        user3.setRole(Role.USER);
        user3.setPhoneNumber("0999245324");

        return Arrays.asList(user1, user2, user3);
    }

    public static List<Long> getListOfIds3() {
        return Arrays.asList(1L, 2L, 3L);
    }
}
