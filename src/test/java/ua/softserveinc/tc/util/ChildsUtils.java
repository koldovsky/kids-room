package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Child;

import java.util.Arrays;
import java.util.List;

public class ChildsUtils {

    public static List<Child> getListOfChilgren()
    {
        Child child1 = new Child();
        Child child2 = new Child();
        Child child3 = new Child();

        return Arrays.asList(child1, child2, child3);
    }

}
