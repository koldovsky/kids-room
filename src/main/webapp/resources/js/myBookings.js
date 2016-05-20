$(document).ready(function(){
	var now = new Date();
	var monthAgo = new Date();
	monthAgo.setMonth(now.getMonth()-1);

	var day = ("0" + now.getDate()).slice(-2);
	var month = ("0" + (now.getMonth() + 1)).slice(-2);
	var toSet = now.getFullYear()+"-"+(month)+"-"+(day);
	$('#to').val(toSet);

	day = ("0" + monthAgo.getDate()).slice(-2);
	month = ("0" + (monthAgo.getMonth() + 1)).slice(-2);
	toSet = monthAgo.getFullYear()+"-"+(month)+"-"+(day);
	$('#from').val(toSet)

	updateTable();
	var refresh = updateTable;

	$('#from').change(refresh);
	$('#to').change(refresh);

});

function updateTable(){
	var from = $('#from').val();
	var to = $('#to').val();
	var request = "mybookings/getbookings?dateLo=" + from +"&dateHi=" + to;

	$.ajax({
		type: "GET",
		url: request,
		datatype: JSON,
		success: function(response){
			var list = $.parseJSON(response);

			var tableContent = '<tr>';
            var sumTotal = 0;
			$.each(list, function(i, booking){
			tableContent += '<td>' + booking.date + '</td>'
            			+ '<td>' + booking.kidName + '</td>'
			            + '<td>' + booking.roomName + '</td>'
			            + '<td>' + booking.startTime + '</td>'
			            + '<td>' + booking.endTime + '</td>'
			            + '<td>' + booking.duration + '</td>'
			            + '<td>' + booking.sum + '</td> + </tr>';
			sumTotal += booking.sum;
			        });

			$('tr:not(#header)').remove();
			$('#myBookings').append( tableContent );

			$('#sum').html(sumTotal);
			
		}
	});
}
