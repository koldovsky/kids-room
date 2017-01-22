package ua.softserveinc.tc.search;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.constants.SearchConstants;
import ua.softserveinc.tc.entity.User;


@Repository
@Transactional
public class UserSearch extends BaseSearch<User> {

    public UserSearch() {
        searchFields = SearchConstants.getUserSearchFields();
    }

    @Override
    public Class<User> getTClass() {
        return User.class;
    }

}
