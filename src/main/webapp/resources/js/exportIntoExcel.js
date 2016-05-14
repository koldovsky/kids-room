$(document).ready(function()
{
    $("#exportButton").click(function (e)
    {
        window.open('data:application/vnd.ms-excel,' + $('#tableToExport').html());
        e.preventDefault();
    });
});