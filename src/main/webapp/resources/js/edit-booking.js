var bookingsArray = [];
var idBooking;
var dateNow = new Date();

$('#data-booking').val(dateNow.toISOString().substr(0,10));


$(function(){
    $('#data-booking').change(refresh);
});
$(function () {
    $('#bookingStartTimepicker').timepicker({
        'timeFormat': 'H:i',
        'step': 15,
        'minTime': '15:00',
        'maxTime': '22:00'
    });
});


$(function () {
    $('#bookingEndTimepicker').timepicker({
        'timeFormat': 'H:i',
        'step': 15,
        'minTime': '15:00',
        'maxTime': '22:00'
    });
});
$(function () {
    $('#bookingUpdatingStartTimepicker').timepicker({
        'timeFormat': 'H:i',
        'step': 15,
        'minTime': '15:00',
        'maxTime': '22:00'
    });
});

$(function () {
    $('#bookingUpdatingEndTimepicker').timepicker({
        'timeFormat': 'H:i',
        'step': 15,
        'minTime': '15:00',
        'maxTime': '22:00'
    });
});


function refresh(){

            var time = $('#data-booking').val();
            var idRoom = localStorage["roomId"];
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
                            + '</tr>';
                        });
                        $('td').remove();
                        $('.table-edit').append(tr);
                    }
           });
}

function changeBooking(id){
      $('#bookingUpdatingDialog').dialog();
      idBooking = id;
}
function createBooking(){
 $('#bookingDialog').dialog();
}

$( document ).ready(function() {
    $('#updatingBooking').click(function() {
        var getData = $('#data').val();
        var stTime = $('#data').val() +" " + $('#bookingUpdatingStartTimepicker').val();
        var edTime = $('#data').val() +" " + $('#bookingUpdatingEndTimepicker').val();
        edit(idBooking, stTime, edTime);
    });
});

function edit(idBooking, stTime, edTime) {

                    var inputDate = {
                        id: idBooking,
                        startTime: stTime,
                        endTime: edTime,
                        roomId:  localStorage["roomId"],
                    };
                    $.ajax({
                        url: 'change-booking',
                        type: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify(inputDate),
                        success: function(data){
                            if(data){
                                alert(data);
                            } else{alert("Noooo"); }
                        }
                    });

}

$( document ).ready(function() {
    $('#deletingBooking').click(function(){
        var str = "cancelBook/"+idBooking;
        $.ajax({
            url: str,
            success: function(result){
                var text = result;
                var bookings = JSON.parse(text);
                $('#' + idBooking).hide();
                $('#bookingUpdatingDialog').dialog('close');
            }
        });
    });
});

/*
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
  }*/
$( document ).ready(function() {
  $('#selectUser').on('change', function(){
       var idUser = $(this).val();
       var url = "get-kids/"+idUser;
       $.ajax({
            url: url,
            success: function(result){
            var kids = JSON.parse(result);
            var kidsCount = kids.length;
            var tr="";
                $.each(kids, function(i, kid){
                   tr+='<div><label><input type="checkbox"'
                   + 'id="checkboxKid'+kid.id+'">'
                   + kid.firstName
                   + '<input type = "text"'+ 'placeholder="Comment"'
                   + 'class="form-control"'
                   + 'id="child-comment-' +kid.id+'">'
                   +'</div>';
                });
            $('#kostil').val(kidsCount);
            $('#kids').append(tr);
            }
       });
  });
});

   function selectRoomForManager(roomId) {
       refresh();
   }

function Booking(startTime, endTime, comment, kidId, roomId, userId) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.comment = comment;
    this.kidId = kidId;
    this.roomId = roomId;
    this.userId =  userId;
}

$( document ).ready(function() {
    $('#booking').click(function () {
         var idUser = $('#selectUser :selected').val();
         var date = $('#bookingStartDate').val();
         var startTime = $('#bookingStartTimepicker').val();
         var endTime = $('#bookingEndTimepicker').val();
         var startISOtime = date + 'T'+ startTime + ':00';
         var endISOtime = date + 'T'+ endTime + ':00';

         var urlForKids = "get-kids/"+idUser;
         $.ajax({
            url: urlForKids,
            success: function(result){
               var kids = JSON.parse(result);
               $.each(kids, function(i, kid){
                    var kidId = kid.id;

                    if ($('#checkboxKid' + kid.id).is(':checked')) {
                       var comment = ($('#child-comment-' +kid.id).val());
                       var booking = new Booking(startISOtime, endISOtime, comment, kidId, localStorage["roomId"], idUser);
                       bookingsArray.push(booking);
                    }
                });
            sendBookingToServerForCreate(bookingsArray);
            }
         });
         $('#bookingDialog').dialog('close');
    });
});

function sendBookingToServerForCreate(bookingsArray) {
     $.ajax({
            type: 'post',
            contentType: 'application/json',
            url: 'makenewbooking',
            dataType: 'json',
            data: JSON.stringify(bookingsArray),
            success: function (result) {
                alert("You create new booking");
            },
            error: function(){
                alert("Unfortunately could not create new BOOKING");
            }
     });
}
