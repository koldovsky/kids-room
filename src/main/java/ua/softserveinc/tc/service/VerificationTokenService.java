package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.entity.VerificationToken;

/**
 * Created by Chak on 17.05.2016.
 */
public interface VerificationTokenService extends BaseService<VerificationToken> {

    public void createToken(String token, User user);

    VerificationToken findByUser(User user);

    VerificationToken findByToken(String token);

}
