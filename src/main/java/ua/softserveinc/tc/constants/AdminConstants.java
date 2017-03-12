package ua.softserveinc.tc.constants;


public final class AdminConstants {
    public static final String EDIT_MANAGER = "adm-edit-manager";

    //Name of *.jsp files of ADMINISTRATOR
    public static final String ADD_MANAGER = "adm-add-manager";
    public static final String UPDATE_MANAGER = "adm-update-manager";
    public static final String EDIT_ROOM = "adm-edit-room";
    public static final String ADD_ROOM = "adm-add-room";
    public static final String UPDATE_ROOM = "adm-update-room";
    public static final String CONFIRM_MANAGER = "adm-confirm-manager";
    public static final String EDIT_CONFIG = "adm-config";
    public static final String ABONNEMENTS = "adm-abonnements";
    public static final String UPDATE_ABONNEMENT = "adm-update-abonnement";
    ///////////////////////////////////////

    public static final String DEACTIVATE_ROOM = "deactivate-room";

    //Used for mark a different Collections of objects
    public static final String MANAGER_LIST = "managerList";
    public static final String ROOM_LIST = "roomList";
    public static final String ATR_MANAGER = "manager";
    public static final String ATR_ROOM = "room";
    public static final String ATR_CONFIG = "config";
    public static final String ATR_ABONNEMENT = "abonnement";

    private AdminConstants() {
    }

    public static class AdminModel {
        public static final String ABONNEMENTS_MODEL = "abonnementsList";
        public static final String ABONNEMENT = "abonnement";
    }

    public static class ViewNames {
        public static final String ABONNEMENTS_VIEW = "adm-abonnements";
        public static final String UPDATE_ABONNEMENT_VIEW = "adm-update-abonnement";
    }

    ///////////////////////////////////////

}
