localStorage["userId"] = $("#selectRoomForParent li a").attr("id").split(",")[1];
$(function () {
    if (localStorage["userRoomId"] == null) {
        var firstRoomAfterLogin = $("#selectRoomForParent li a").first().attr("id");
        var managersArray = firstRoomAfterLogin;
        var roomInfo = firstRoomAfterLogin.split(",");
        var roomId = roomInfo[0];
        var phoneNumber = roomInfo[2];
        var address = $("#selectRoomForParent li a").first().text();
        var managers = getManagersNames(managersArray);
        selectRoomForUser(roomId,localStorage["userId"],phoneNumber,managers);
        $("#usersRoom").html(address+ ' ' + '<span id="glyph" class=" glyphicon glyphicon-arrow-left"></span>');
        renderRoom();
    } else {
        selectRoomForUser(localStorage["userRoomId"], localStorage["userId"], localStorage["phoneNumber"], localStorage["managers"]);
        $("#usersRoom").text(localStorage["address"]);
        renderRoom();
    }

});
function getManagersNames(arr) {
    var start_pos = arr.indexOf('[') + 1;
    var end_pos = arr.indexOf(']', start_pos);
    var managers = arr.substring(start_pos, end_pos);
    return managers;

}
function renderRoom() {
    $("#selectRoomForParent li a").each(function () {
        $(this).click(function () {
            var managersString = $(this).attr("id");
            var room = $(this).first().attr("id").split(",");

            localStorage["userRoomId"] = room[0];
            localStorage["phoneNumber"] = room[2];
            localStorage["managers"] = getManagersNames(managersString);
            localStorage["address"] = $(this).text();


            selectRoomForUser(localStorage["userRoomId"], localStorage["userId"], localStorage["phoneNumber"], localStorage["managers"]);
            $("#usersRoom").text(localStorage["address"]);

        });

    });
}