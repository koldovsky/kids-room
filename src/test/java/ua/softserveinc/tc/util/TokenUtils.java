package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Token;
import ua.softserveinc.tc.entity.User;

import java.util.Arrays;
import java.util.List;

public class TokenUtils {

    public static List<Token> getListOfTokens() {

        Token token1 = createToken(1L, "abcd", UserUtils.getListOfUser().get(0));
        Token token2 = createToken(2L, "second token", UserUtils.getListOfUser().get(1));

        return Arrays.asList(token1, token2);
    }

    public static Token createToken(Long id, String tokenStr, User user) {

        Token token = new Token();
        token.setId(id);
        token.setToken(tokenStr);
        token.setUser(user);

        return token;
    }

}
