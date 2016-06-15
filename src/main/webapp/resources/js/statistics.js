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
            var managers = room.namesOfManagers.join('</br>');;

            tr += '<tr><td>' + room.name + '</td>'
            + '<td>' + room.city + '</td>'
            + '<td>' + room.address + '</td>'
            + '<td>' + managers + '</td>'
            + '<td>' + room.sum + '</td></tr>'
        });

        $("tr:not(#header)").remove();

        $('#statistics').append(tr);
    }});
}