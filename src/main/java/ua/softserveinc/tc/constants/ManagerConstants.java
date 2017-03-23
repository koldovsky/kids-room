package ua.softserveinc.tc.constants;


public final class  ManagerConstants {

    //String to access mapping controller of Manager role
    public static final String MANAGER_CALENDAR = "manager-calendar";
    public static final String MANAGER_REPORT = "manager-report";
    public static final String MANAGER_REPORT_ALL = "manager-report-all";
    public static final String MANAGER_REPORT_PARENT = "manager-report-parent";
    public static final String MANAGER_EDIT_BOOKING = "manager-edit-booking";

    private ManagerConstants(){

    }

    public static class ManagerViewNames{
        //names of jps views for Manager role
        public static final String MANAGER_CALENDAR_VIEW = "manager-calendar";
        public static final String MANAGER_REPORT_VIEW = "report";
        public static final String MANAGER_REPORT_ALL_VIEW = "report-all";
        public static final String MANAGER_REPORT_PARENT_VIEW = "report-parent";
        public static final String MANAGER_EDIT_BOOKING_VIEW = "manager-edit-booking";
    }
}
