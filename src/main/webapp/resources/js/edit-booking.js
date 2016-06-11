$(function(){
    $('#data-booking').change(refresh);
});

function refresh(){

            var time = $('#data-booking').val();
            var idRoom = localStorage["room"];
            var src = 'manager-edit-booking/'+time + "/" +idRoom;
            $.ajax({
                    url: src,
                    success: function(result){
                        var bookings = JSON.parse(result);
                        var tr = "";
                        var cancel = '<spring:message code= "booking.canceled"/>';
                        $.each(bookings, function(i, booking){
                        var changeButton = 'onclick='+'"'+'changeBooking(' +booking.id +')"';
                        var cancelButton = 'onclick='+'"'+'changeBooking(' +booking.id +')"';
                            tr+= '<tr id=' + booking.id +'><td>'
                            + '<a href=profile?id=' + booking.idChild +'>'
                            + booking.kidName +'</td>'
                            + '<td>' + booking.startTime + " -" + booking.endTime +  '</td>'
                            +'<td class="change-booking">'
                            +'<button class="btn btn-sm btn-primary"'
                            + 'data-toggle="modal"'
                            + 'data-target="#change-booking-modal"'
                            + changeButton  +'> Edit </button>'
                            + '</td>'
                            + '<td class="cancelClass">'
                            + '<button class="btn btn-sm btn-warning"'
                            + 'data-toggle="modal"'
                            + 'data-target="#cancelModal"'
                            + cancelButton +'> Cancel </button>'
                            + '</td>'
                            + '</tr>';
                        });
                        $('td').remove();
                        $('.table-edit').append(tr);
                    }
           });
}


 function changeBooking(id){

             $('#wrong-interval').hide();
             $('#fill-in').hide();
             var idElement="#"+id;
            $('#change-booking-modal').find('#change-booking').click(function(){
                    var getData = $(this).closest('.modal-dialog');
                    var inputDate = {
                        id: id,
                        startTime: getData.find('#data').val()+" "+getData.find('#startTime').val(),
                        endTime: getData.find('#data').val()+" "+ getData.find('#endTime').val(),
                    };
                    $.ajax({
                        url: 'change-booking',
                        type: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify(inputDate),
                        success: function(data){
                            if(!data){
                                $('#wrong-interval').show();
                            } else

                            $('#change-booking-modal').modal('hide');
                        }
                    });

            });

 }

 function selectSelectKid(id){

         $('#send-button').click(function(){
             var date = $('#create-date').val();
             var stTime = date+ " "+$('#create-start-time').val();
             var enTime = date+ " "+$('#create-end-time').val();
             var comment = $('#create-comment').val();
             var inputDate = {
                 startTime:  stTime,
                 endTime: enTime,
                 idChild: id,
                 comment: comment,
             };

             $.ajax({
                 url: 'create-booking',
                 type: 'POST',
                 contentType: 'application/json',
                 data: JSON.stringify(inputDate),
                 success: function(data){
                 }
             });
         });
  }

   function selectUser(user){
      alert(user);
   }

   function selectRoomForManager(room) {
       localStorage["room"] = room;
       // Vasyl code goes here
       refresh();
   }