$(function(){
    refreshView();
    $("#startDate, #endDate").change(refreshView);
});

function refreshView()
{
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
	var request = "refreshRooms/";
    request += startDate + "/" + endDate;

    $.ajax({url: request, success: function(result)
    {
        var rooms = JSON.parse(result);

        var tr = "";

        $.each(rooms, function(i, room)
        {
            tr += '<tr><td>' + room.name + '</td>'
            + '<td>' + room.city + '</td>'
            + '<td>' + room.address + '</td>'
            + '<td>' + room.manager.firstName + " " + room.manager.lastName + '</td>'
            + '<td>' + room.sum + '</td></tr>'
        });

        $('td').remove();

        $('#statistics').append(tr);
    }});
}