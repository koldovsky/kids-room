$(function(){
    if(localStorage["room"] == null) {
        localStorage["room"] = $("#selectRoom li a").first().attr("id");
    }

    $("#selectRoom li a").each(function() {
        $(this).click(function() {
            var id = $(this).attr("id");
            localStorage["room"] = id;
            selectRoomForManager(id);
        });
    });

    selectRoomForManager(localStorage["room"]);
});