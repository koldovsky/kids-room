package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.entity.User;

/**
 * Created by Demian on 16.05.2016.
 */
public class UserDTO implements BaseDTO
{
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public UserDTO(User user)
    {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }
}
