$(function(){
    $("#dateNowInput, #dateThenInput").change(refreshView);
});

function refreshView()
{
    var dateThen = $("#dateThenInput").val();
    var dateNow = $("#dateNowInput").val();
	var request = "refreshRooms/";
    request += dateThen + "/" + dateNow;

    $.ajax({url: request, success: function(result)
    {
        var rooms = JSON.parse(result);

        $('#date').remove();
        var caption = $('caption h2').html();
        caption += '<span id="date">(' + dateThen + ' - ' + dateNow + ')</span>';
        $( 'caption h2' ).html(caption);

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

        $('#activeUsers').append(tr);

        addListener();
    }});
}