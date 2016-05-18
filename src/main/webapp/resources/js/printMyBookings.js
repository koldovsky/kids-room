$(document).ready(function(){
    $('#print').click(function() {
                            var divToPrint = document.getElementById('myBookings');
                            newWin = window.open("");
                            newWin.document.write(divToPrint.outerHTML);
                            newWin.print();
                            newWin.close();
                         });
})