document.addEventListener("DOMContentLoaded", function ()
{
	var rows = document.getElementsByClassName("parentRow");
	var rowsCount = rows.length;
	for (var i = 0; i < rowsCount; i++)
	{
		rows[i].onclick = function(e)
		{
			document.getElementById("parentEmailField").value = this.id;
			document.getElementById("dateThenField").value = document.getElementById("dateThenInput").value;
			document.getElementById("dateNowField").value = document.getElementById("dateNowInput").value;
			document.getElementById("parentBookingsForm").submit();
		};
	}
});