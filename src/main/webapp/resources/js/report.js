$(function(){
    localizedDetails = $("#seeDetails").html();
    addListener();
    $("#dateNow, #dateThen").change(refreshView);
    $("#generateAReport").submit(function(){
        $("#roomIdHidden").val($("#selectRoom").val());
    });
});

function addListener()
{
    $(".parent").each(function(){
        $(this).click(function(){
            $("#parentEmailHidden").val($(this).attr('id'));
            $("#dateNowHidden").val($("#dateNow").val());
            $("#dateThenHidden").val($("#dateThen").val());
            $("#allBookingsPerParentForm").submit();
        });
    });
};

function refreshView()
{
    var room = $("#selectRoom").val();
    var dateNow = $("#dateNow").val();
    var dateThen = $("#dateThen").val();
	var request = "refreshParents/";
    request += room + "/" + dateThen + "/" + dateNow;

    $.ajax({url: request, success: function(result)
    {
        var users = JSON.parse(result);

        $('#date').remove();
        var caption = $('caption h2').html();
        caption += '<span id="date">(' + dateThen + ' - ' + dateNow + ')</span>';
        $( 'caption h2' ).html(caption);

        var tr = "";

        $.each(users, function(i, user)
        {
            tr += '<tr><td>' + user.firstName + '</td>'
            + '<td>' + user.lastName + '</td>'
            + '<td>' + user.email + '</td>'
            + '<td>' + user.phoneNumber + '</td>'
            + '<td class="parent" id="'
            + user.email + '"><a>' + localizedDetails + '</a></td></tr>';
        });

        $('td').remove();

        $('#activeUsers').append(tr);

        addListener();
    }});
}

function selectRoomForManager(room)
{
    localStorage["room"] = room;
    refreshView();
}