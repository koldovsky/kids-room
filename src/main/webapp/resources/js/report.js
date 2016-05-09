document.addEventListener("DOMContentLoaded", function ()
{
	var rows = document.getElementsByClassName("parentRow");
	var rowsCount = rows.length;
	for (var i = 0; i < rowsCount; i++)
	{
		rows[i].onclick = function(e)
		{
			document.getElementById("hiddenField").value = this.id;
			document.getElementById("parentBookings").submit();
		};
	}
});