package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;

public interface TokenService extends BaseService<Token> {

    public void createToken(String token, User user);

    Token findByUser(User user);

    Token findByToken(String token);

}
