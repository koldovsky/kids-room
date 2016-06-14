package ua.softserveinc.tc.validator;

/**
 * Created by Nestor on 05.06.2016.
 */
public class LogicalRequestsValidator {
    private LogicalRequestsValidator(){

    }

    public static boolean isRequestValid(String requestParam){
        return requestParam.matches("[0-9]+") && !requestParam.isEmpty();
    }
}
