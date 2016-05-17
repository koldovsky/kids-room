package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.VerificationTokenDao;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.entity.VerificationToken;

/**
 * Created by Chak on 17.05.2016.
 */
@Service
public class VerificationTokenServiceImpl extends BaseServiceImpl<VerificationToken> implements VerificationTokenService {

    @Autowired
    private VerificationTokenDao tokenDao;

    @Override
    public void createToken(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        tokenDao.create(verificationToken);
    }

    @Override
    public VerificationToken findByUser(User user) {
        return tokenDao.findByUser(user);
    }

    @Override
    public VerificationToken findByToken(String token) {
        return tokenDao.findByToken(token);
    }
}
