package ua.softserveinc.tc.validator;

/**
 * a "Validator" class that helps checking requests from client
 */
public class LogicalRequestsValidator {
    private LogicalRequestsValidator(){
    }

    public static boolean isRequestValid(String requestParam){
        return requestParam.matches("[0-9]+") && !requestParam.isEmpty();
    }
}
