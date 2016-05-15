document.addEventListener("DOMContentLoaded", function ()
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

	document.getElementById("dateNowInput").onchange = refreshView;
	document.getElementById("dateThenInput").onchange = refreshView;

	function refreshView()
	{
	    var request = "refreshParents/";
	    request += $("#dateThenInput").val() + "/" + $("#dateNowInput").val();
	    $.ajax({url: request, success: function(result)
	        {
	            var obj = JSON.parse(result);

	            for(var i = 0; i < obj.users.length; i++)
	            {
	                alert(obj.users[i].email);
	            }
	        }
	    });
    }
});