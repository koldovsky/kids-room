package ua.softserveinc.tc.constants;


/**
 * Created by edward on 5/17/16.
 */
public interface ApiConstants {

    String usersRestUrl = "/api/user";
    String usersRestByIdUrl = "/api/user/{id}";
    String childrenRestUrl = "/api/child";
    String childrenByIdRestUrl = "/api/child/{id}";
    String getChildrenParentRestUrl = "/api/child/{id}/parent";
    String getAppConfiguration = "/api/configuration";

}
