$(function() {
    refreshView();
    addListenerForDetails();
    addListenerForGenerate();
    $("#dateNow, #dateThen").change(refreshView);
});

function getObjectsToSent() {
    var objects = {
        roomId: $("#selectRoom").val(),
        dateNow: $("#dateNow").val(),
        dateThen: $("#dateThen").val()
    }
    return objects;
}

function addListenerForDetails() {
    $(".parent").each(function() {
        $(this).click(function() {
            var objects = getObjectsToSent();
            objects["parentEmail"] = $(this).attr("id");
            $.redirect("/home/report-parent", objects);
        });
    });
};

function addListenerForGenerate() {
    $("#generate").click(function() {
        $.redirect("/home/report-all", getObjectsToSent());
    });
};

function refreshView() {
    var objects = getObjectsToSent();
	var request = "refreshParents/" + objects["roomId"] + "/";
    request += objects["dateThen"] + "/" + objects["dateNow"];

    $.ajax({url: request, success: function(result)
    {
        var users = JSON.parse(result);

        $('#date').remove();
        var caption = $('caption h2').html();
        caption += '<span id="date">(' + objects["dateThen"] + ' - ' + objects["dateNow"] + ')</span>';
        $( 'caption h2' ).html(caption);

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
    localStorage["room"] = room;
    refreshView();
}