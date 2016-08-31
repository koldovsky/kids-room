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

			var tableContent = '<tbody><tr>';
            var sumTotal = 0;
			$.each(list, function(i, booking){
			tableContent += '<td>' + booking.date + '</td>'
            			+ '<td>' + booking.kidName + '</td>'
			            + '<td>' + booking.roomName + '</td>'
			            + '<td>' + booking.startTime + '</td>'
			            + '<td>' + booking.endTime + '</td>'
			            + '<td>' + booking.duration + '</td>'
			            + '<td>' + (booking.sum/100).toFixed(2) + '</td> + </tr>';
			sumTotal += booking.sum;
			        });

            tableContent += '</tbody>';
			$('tr:not(#header)').remove();
			$('#myBookings').append( tableContent );
            sumTotal = (sumTotal/100).toFixed(2);
			$('#sum').html(sumTotal);
			paginate();
		}
	});
}

function paginate(){
     $('.pager').remove();
     $('#myBookings').each(function() {
            var currentPage = 0;
            var numPerPage = parseInt( $( '#itemsPerPage' ).val());
            var $table = $(this);
            $table.bind( 'repaginate', function() {
                $table.find('tbody tr').hide().slice(currentPage * numPerPage, (currentPage + 1) * numPerPage).show();
            });
            $table.trigger('repaginate');
            var numRows = $table.find( 'tbody tr' ).length;
            var numPages = Math.ceil(numRows / numPerPage);
            var $pager = $( '<div class="pager"></div>' );
            for (var page = 0; page < numPages; page++) {
                $( '<span class="page-number"></span>').text(page + 1).bind('click', {
                    newPage: page
                }, function(event) {
                    currentPage = event.data['newPage'];
                    $table.trigger( 'repaginate' );
                    $(this).addClass( 'active' ).siblings().removeClass( 'active' );
                }).appendTo($pager).addClass( 'clickable' );
            }
            $pager.insertBefore( $table ).find( 'span.page-number:first' ).addClass( 'active' );
        });
}

var tableToExcel = ( function() {
          var uri = 'data:application/vnd.ms-excel;base64,'
          , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
          , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
          , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
          return function (table, name) {
                      $('#itemsPerPage').val(Number.MAX_SAFE_INTEGER);
                      paginate();
                      var filename = $('#from').val() + "--" + $('#to').val();
                      if (!table.nodeType) table = document.getElementById(table)
                      var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }

                      document.getElementById("dlink").href = uri + base64(format(template, ctx));
                      document.getElementById("dlink").download = filename;
                      document.getElementById("dlink").click();
                      $('#itemsPerPage').val(10);
                      paginate();
                      }
          })()