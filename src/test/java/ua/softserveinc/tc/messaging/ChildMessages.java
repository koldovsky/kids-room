package ua.softserveinc.tc.messaging;

public final class ChildMessages {

    public static final String INCORRECT_LIST_OF_CHILDREN =
            "Method getActiveChildreInRoom() return incorrect list of children";

    public static final String INCORRECT_GET_ACTIVE_CHILDREN_IN_ROOM_WHEN_ROOM_IS_NOT_ACTIVE =
            "This booking is not active, so there shouldn't be active children in it";

    public static final String INCORRECT_GET_ACTIVE_CHILDREN_IN_ROOM_WHEN_ROOM_IS_ACTIVE =
            "This booking is active, so there should be active children in it";

    public static final String INCORRECT_GET_ACTIVE_CHILDREN_IN_ROOM =
            "Method getActiveChildrenInRoom(room) doesn't work correct";
}
