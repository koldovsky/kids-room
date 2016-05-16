package ua.softserveinc.tc.search;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.softserveinc.tc.entity.User;

/**
 * Created by edward on 5/14/16.
 */
@Repository
@Transactional
public class UserSearch extends BaseSearch<User> {

    {
        searchFields = new String[]{"firstName", "lastName", "email", "phoneNumber"};
    }

    @Override
    public Class<User> getTClass() {
        return User.class;
    }

}
