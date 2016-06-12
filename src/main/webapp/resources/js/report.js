$(function() {
    addListenerForDetails();
    addListenerForGenerate();
    $("#startDate, #endDate").change(refreshView);
});

function getObjectsToSent() {
    var objects = {
        startDate: $("#startDate").val(),
        endDate: $("#endDate").val(),
        roomId: localStorage["room"]
    }
    return objects;
}

function addListenerForDetails() {
    $(".parent").each(function() {
        $(this).click(function() {
            var objects = getObjectsToSent();
            objects["email"] = $(this).attr("id");
            $.redirect("manager-report-parent", objects);
        });
    });
};

function addListenerForGenerate() {
    $("#generate").click(function() {
        $.redirect("manager-report-all", getObjectsToSent());
    });
};

function refreshView() {
    var objects = getObjectsToSent();
	var request = "refreshParents/" + objects["startDate"] + "/";
    request += objects["endDate"] + "/" + objects["roomId"];

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
            + user.email + '"><a>See details</a></td></tr>';
        });

        $('td').remove();

        $('#activeUsers').append(tr);

        addListenerForDetails();
    }});
}

function selectRoomForManager(room) {
    refreshView();
}