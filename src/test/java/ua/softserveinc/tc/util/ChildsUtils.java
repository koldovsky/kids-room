package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Gender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ChildsUtils {

    public static List<Child> getListOfChilgren() throws ParseException {
        Child child1 = new Child();

        child1.setId(1L);
        child1.setFirstName("Adam");
        child1.setLastName("First");
        child1.setParentId(UserUtils.getListOfUser().get(4));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String startDateInString = "2016-02-12";
        Date dateOfBirth = sdf.parse(startDateInString);

        child1.setDateOfBirth(dateOfBirth);
        child1.setComment("he likes to play poker");
        child1.setGender(Gender.MALE);

        Child child2 = new Child();
        Child child3 = new Child();

        return Arrays.asList(child1, child2, child3);
    }

}
