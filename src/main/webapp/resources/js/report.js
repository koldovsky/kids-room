document.addEventListener("DOMContentLoaded", function ()
{
	var divs = document.getElementsByTagName("div");
	var divsCount = divs.length;
	for (var i = 0; i < divsCount; i++)
	{
		divs[i].onclick = function(e)
		{
			document.getElementById("hiddenField").value = this.id;
			e.stopPropagation();
			document.getElementById("parentBookings").submit();
		};
	}
});