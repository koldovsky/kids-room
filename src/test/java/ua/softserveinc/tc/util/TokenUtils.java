package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Token;

import java.util.Arrays;
import java.util.List;

public class TokenUtils {

    public static List<Token> getListOfTokens() {
        Token token1 = new Token();

        token1.setId(1L);
        token1.setToken("first token");
        token1.setUser(UserUtils.getListOfUser().get(0));

        Token token2 = new Token();

        token2.setId(1L);
        token2.setToken("second token");
        token2.setUser(UserUtils.getListOfUser().get(1));

        return Arrays.asList(token1, token2);
    }

}
