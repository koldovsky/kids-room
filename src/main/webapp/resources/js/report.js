$(function(){
    addListener();
    $("#dateNowInput, #dateThenInput").change(refreshView);
});

function addListener()
{
    $(".parent").each(function(){
        $(this).click(function(){
            $("#parentEmailHidden").val($(this).attr('id'));
            $("#dateNowHidden").val($("#dateNowInput").val());
            $("#dateThenHidden").val($("#dateThenInput").val());
            $("#allBookingsPerParentForm").submit();
        });
    });
};

function refreshView()
{
    var dateThen = $("#dateThenInput").val();
    var dateNow = $("#dateNowInput").val();
	var request = "refreshParents/";
    request += dateThen + "/" + dateNow;

    $.ajax({url: request, success: function(result)
    {
        var users = JSON.parse(result);
        alert(JSON.stringify(users, null, 4));

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
            + user.email + '"><a>See details</a></td></tr>';
        });

        $('td').remove();

        $('#activeUsers').append(tr);

        addListener();
    }});
}