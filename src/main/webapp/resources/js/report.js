$(function () {
    addListenerForDetails();
    addListenerForGenerate();
    $("#startDate, #endDate").change(refreshView);
});

function addListenerForDetails() {
    $(".parent").each(function () {
        $(this).click(function () {
            window.location.href ="manager-report-parent"
                + "?startDate=" + $("#startDate").val()
                + "&endDate=" + $("#endDate").val()
                + "&roomId=" + localStorage["roomId"]
                + "&email=" + $(this).attr("id");
        });
    });
}

function addListenerForGenerate() {
    $("#generate").click(function () {
        window.location.href = "manager-report-all"
            + "?startDate=" + $("#startDate").val()
            + "&endDate=" + $("#endDate").val()
            + "&roomId=" + localStorage["roomId"];
    });
}


function refreshView() {
    var request = "restful/manager-report/" + $("#startDate").val() + "/";
    var endDate = moment($("#endDate").val()).add(1, 'day').format("YYYY-MM-DD");
    request += endDate + "/" + localStorage["roomId"];
    if(validateDate()) {
        $.ajax({
            url: request,
            type: 'GET',
            success: function (result) {
                var users = result;

                var tr = "";

                $.each(users, function (i, user) {
                    tr += '<tr><td>' + user.firstName + '</td>'
                        + '<td>' + user.lastName + '</td>'
                        + '<td>' + user.email + '</td>'
                        + '<td>' + user.phoneNumber + '</td>'
                        + '<td class="parent" id="'
                        + user.email + '"><a>' + $("#localizedDetails").val()
                        + '</a></td></tr>';
                });

                $("tr:not(#header)").remove();

                $("#bookings").append(tr);

                addListenerForDetails();
                paginate();
            }
        });
    }
}

function selectRoomForManager(room) {
    refreshView();
}
