$(function() {
    paginate();
});

function paginate(){
     $('.pager').remove();
     $('#bookings').each(function() {
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
                      var filename = $('#startDate').val() + " — " + $('#endDate').val();
                      if (!table.nodeType) table = document.getElementById(table)
                      var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }

                      document.getElementById("dlink").href = uri + base64(format(template, ctx));
                      document.getElementById("dlink").download = filename;
                      document.getElementById("dlink").click();
                      $('#itemsPerPage').val(10);
                      paginate();
                      }
          })()