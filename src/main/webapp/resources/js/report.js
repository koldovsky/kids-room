$(function() {
    addListenerForDetails();
    addListenerForGenerate();
    $("#startDate, #endDate").change(refreshView);
});

function addListenerForDetails() {
    $(".parent").each(function() {
        $(this).click(function() {
            window.location.replace("manager-report-parent"
            + "?startDate=" + $("#startDate").val()
            + "&endDate=" + $("#endDate").val()
            + "&roomId=" + localStorage["roomId"]
            + "&email=" + $(this).attr("id"));
        });
    });
};

function addListenerForGenerate() {
    $("#generate").click(function() {
        window.location.replace("manager-report-all"
        + "?startDate=" + $("#startDate").val()
        + "&endDate=" + $("#endDate").val()
        + "&roomId=" + localStorage["roomId"]);
    });
};

function refreshView() {
	var request = "refreshParents/" + $("#startDate").val() + "/";
    request += $("#endDate").val() + "/" + localStorage["roomId"];

    $.ajax({url: request, success: function(result)
    {
        var users = JSON.parse(result);

        var tr = "";

        $.each(users, function(i, user)
        {
            tr += '<tr><td>' + user.firstName + '</td>'
            + '<td>' + user.lastName + '</td>'
            + '<td>' + user.email + '</td>'
            + '<td>' + user.phoneNumber + '</td>'
            + '<td class="parent" id="'
            + user.email + '"><a>' + $("#localizedDetails").val() + '</a></td></tr>';
        });


        $("tr:not(#header)").remove();

        $("#bookings").append(tr);

        addListenerForDetails();
        paginate();
    }});
}

function selectRoomForManager(room) {
    refreshView();
}