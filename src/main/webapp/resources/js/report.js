document.addEventListener("DOMContentLoaded", function ()
{
    addListener();
	document.getElementById("dateNowInput").onchange = refreshView;
	document.getElementById("dateThenInput").onchange = refreshView;
});

function addListener()
{
	var rows = document.getElementsByClassName("parentRow");
	var rowsCount = rows.length;
	for (var i = 0; i < rowsCount; i++)
	{
		rows[i].onclick = function(e)
		{
			document.getElementById("parentEmailHidden").value = this.id;
			document.getElementById("dateNowHidden").value = document.getElementById("dateNowInput").value;
			document.getElementById("dateThenHidden").value = document.getElementById("dateThenInput").value;
			document.getElementById("allBookingsPerParentForm").submit();
		};
	}
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

        $('#date').remove();
        var caption = $('caption h2').html();
        caption += '<span id="date">(' + dateThen + ' - ' + dateNow + ')</span>';
        $( 'caption h2' ).html(caption);

        var tr = '<tr>';

        $.each(users, function(i, user)
        {
            tr += '<td>' + user.firstName + '</td>'
            + '<td>' + user.lastName + '</td>'
            + '<td>' + user.email + '</td>'
            + '<td>' + user.phoneNumber + '</td>'
            + '<td class="parentRow" id="'
            + user.email + '"><a>See details</a></tr>';
        });

        $('tr').remove();

        $('#activeUsers').append(tr);

        addListener();
    }});
}