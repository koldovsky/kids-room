$(function(){
    var person = prompt("Please enter your name", localStorage['bahrianyi']);
    localStorage['bahrianyi'] = person;
    localizedDetails = $("#seeDetails").html();
    addListener();
    $("#dateNow, #dateThen").change(refreshView);
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
    var dateThen = $("#dateThen").val();
    var dateNow = $("#dateNow").val();
	var request = "refreshParents/";
    request += dateThen + "/" + dateNow;

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