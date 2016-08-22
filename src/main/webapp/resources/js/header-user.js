$(function () {
    if (localStorage["roomId"] == null) {
        localStorage["room"] = $("#usersRoom li a").first().text();
        localStorage["roomId"] = $("#usersRoom li a").first().attr("id");
    }

    $("#selectRoomForParent li a").each(function () {
        $(this).click(function () {
            var managersString = $(this).attr("id");
            var room = $(this).first().attr("id").split(",");

            localStorage["roomId"] = room[0];
            localStorage["userId"] = room[1];
            localStorage["phoneNumber"] = room[2];
            localStorage["managers"] = getManagersNames(managersString);
            localStorage["address"] = $(this).text();


            selectRoomForUser(localStorage.roomId, localStorage.userId, localStorage.phoneNumber, localStorage.managers);
            $("#usersRoom").text(localStorage["address"]);

        });

    });

});
function getManagersNames(arr) {
    var start_pos = arr.indexOf('[') + 1;
    var end_pos = arr.indexOf(']', start_pos);
    var managers = arr.substring(start_pos, end_pos);
    return managers;

}

