package ua.softserveinc.tc.json;

import ua.softserveinc.tc.dto.UserDTO;

import java.util.List;

/**
 * Created by Demian on 17.05.2016.
 */
public class UserJSON
{
    private List<UserDTO> users;
    private String startDate;
    private String endDate;

    public UserJSON(List<UserDTO> users, String startDate, String endDate)
    {
        this.users = users;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
