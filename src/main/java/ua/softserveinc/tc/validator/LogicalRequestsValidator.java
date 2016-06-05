package ua.softserveinc.tc.validator;

/**
 * Created by Nestor on 05.06.2016.
 */
public class LogicalRequestsValidator {
    public static boolean isRequestValid(String requestParam){
        if(requestParam.matches("[0-9]+") && !requestParam.isEmpty()) {
            return true;
        }
        else return false;
    }
}
