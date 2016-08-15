$(function () {
    var room;

    $("#selectRoomForParent li a").each(function () {
        $(this).click(function () {
            var managersString = $(this).attr("id");
            room = $(this).first().attr("id").split(",");

            localStorage["roomId"] = room[0];
            localStorage["userId"] = room[1];
            localStorage["roomCapacity"] = room[2];
            localStorage["phoneNumber"] = room[3];
            localStorage["manager"] = getManagersNames(managersString);


            selectRoomForUser(localStorage.roomId, localStorage.userId, localStorage.roomCapacity, localStorage.phoneNumber, localStorage.manager);
            $("#usersRoom").text($(this).text());

        });

    });
    function getManagersNames(arr) {
        var start_pos = arr.indexOf('[') + 1;
        var end_pos = arr.indexOf(']', start_pos);
        var managers = arr.substring(start_pos, end_pos);
        return managers;

    }

});
