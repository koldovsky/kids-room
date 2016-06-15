$(function() {
    if(localStorage["roomId"] == null) {
        localStorage["room"] = $("#selectRoom li a").first().text();
        localStorage["roomId"] = $("#selectRoom li a").first().attr("id");
    }

    $("#selectRoom li a").each(function() {
        $(this).click(function() {
            var room = $(this).text();
            var roomId = $(this).attr("id");

            localStorage["room"] = room;
            localStorage["roomId"] = roomId;

            getAmountOfChildren();
            $("#room").text(room);
            selectRoomForManager(roomId);
        });
    });

    getAmountOfChildren();
    $("#room").text(localStorage["room"]);
    selectRoomForManager(localStorage["roomId"]);
});

function getAmountOfChildren() {
    $.ajax({
        url: "getAmountOfChildren/" + localStorage["roomId"],
        success: function(result) {
        $("#amountOfChildren").text(result)
    }});
}