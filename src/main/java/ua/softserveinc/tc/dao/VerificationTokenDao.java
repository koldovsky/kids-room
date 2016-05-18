package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.entity.VerificationToken;

/**
 * Created by Chak on 17.05.2016.
 */
public interface VerificationTokenDao extends BaseDao<VerificationToken> {
    VerificationToken findByUser(User user);

    VerificationToken findByToken(String token);

    public void delete(VerificationToken entity);
}
