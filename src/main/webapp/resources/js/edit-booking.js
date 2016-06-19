var bookingsArray = [];
var idBooking;
var dateNow = new Date();
var bookingsState= ['BOOKED'];


$('#data-booking').val(dateNow.toISOString().substr(0,10));

$(function(){
  $('#data-booking').change(function(){
    refresh(bookingsState);
  });
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

function bookedBooking(){
    bookingsState= ['BOOKED'];
    refresh(bookingsState);
}
function activeBooking(){
    bookingsState = ['ACTIVE'];
    refresh(bookingsState);
}
function leavedBooking(){
    bookingsState = ['COMPLETED'];
    refresh(bookingsState);
}
function allBooking(){
    bookingsState = ['ACTIVE', 'BOOKED', 'CALCULATE_SUM', 'COMPLETED'];
    refresh(bookingsState);
}


function refresh(bookingsState){
  var time = $('#data-booking').val();
  var idRoom = localStorage["roomId"];
  var src = 'dailyBookings/'+time + "/" +idRoom + "/" +bookingsState;
  $.ajax({
    url: src,
    success: function(result){
      var bookings = JSON.parse(result);
      var tr = "";
      $.each(bookings, function(i, booking){
        var index = i+1;
        var startButton = 'onclick='+'"'+'setStartTime(' +booking.id +')"';
        var endButton = 'onclick='+'"'+'setEndTime(' +booking.id +')"';
        var editButton = 'onclick='+'"'+'changeBooking(' +booking.id +')"';
        var setStatrTimeInput = 'onclick='+'"'+'setStatrTimeInput('+booking.id +')"';
        var setEndTimeInput = 'onclick='+'"'+'setEndTimeInput('+booking.id +')"';
        tr+= '<tr id=' + booking.id +'><td>'
        + index
        +'</td><td>'
        + '<a href=profile?id=' + booking.idChild +'>'
        + booking.kidName +'</td>'
        + '<td >' + '<span id ="start-time">' + booking.startTime + '</span>' + " - "
        + '<span id ="end-time">' + booking.endTime + '</span>' + " "
        + '<button class="btn btn-sm glyphicon glyphicon-edit" '
        + editButton + '></button></td>'
        + '<td><div class="input-group">'
        + '<input type="time"' + 'id="arrivalTime"'+ 'class="form-control"'
        + setStatrTimeInput +'/>'

        + '<input type="button"'+'class="btn btn-raised btn-sm btn-info"'
        + startButton +'value="Set"'+'</input></td></div>'
        + '<td><div class="input-group"><input required type="time"' + 'id="leaveTime"'+ 'class="form-control"'
        + setEndTimeInput + '/>'

        + '<input required type="button"'+'class="btn btn-raised btn-sm btn-info"'
        + endButton +'value="Set"'+'</input></td></div>'
        + '</tr>';
      });
      $('td').remove();
      $('.table-edit').append(tr);
      addHilighted(bookings);
    }
  });
}

function addHilighted(bookings){
 $.each(bookings, function(index, value) {
  if(value.bookingState=="ACTIVE"){
    $('#'+value.id).addClass('highlight-active');
    $('#'+value.id).find('#arrivalTime').val(value.startTime);
  }else if(value.bookingState=="COMPLETED"){
    $('#'+value.id).addClass('highlight-complet');
    $('#'+value.id).find('#arrivalTime').val(value.startTime);
    $('#'+value.id).find('#leaveTime').val(value.endTime);
  }
 });
}

function setStatrTimeInput(id){
  var time = dateNow.toString().match(/\d{2}:\d{2}/)[0];
  $('#'+id).find('#arrivalTime').val(time);
}
function setEndTimeInput(id){
  var time = dateNow.toString().match(/\d{2}:\d{2}/)[0];
  $('#'+id).find('#leavetime').val(time);
}

function changeBooking(id){
  var date = $('#data-booking').val();
  var startTime = $('#' + id).find('#start-time').text();
  var endTime = $('#' + id).find('#end-time').text();
  $('#bookingUpdatingStartTimepicker').val(startTime);
  $('#bookingUpdatingEndTimepicker').val(endTime);
  $('#data-edit').val(date);
  $('#bookingUpdatingDialog').dialog();
  idBooking = id;
}
function createBooking(){
  var date = $('#data-booking').val();
  $('#bookingStartDate').val(date);
  $('#bookingDialog').dialog();

}

$( document ).ready(function() {
  $('#updatingBooking').click(function() {
    var getData = $('#data-edit').val();
    var stTime = $('#data-edit').val() +" " + $('#bookingUpdatingStartTimepicker').val();
    var edTime = $('#data-edit').val() +" " + $('#bookingUpdatingEndTimepicker').val();
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
        alert("Updating success");
        $('#bookingUpdatingDialog').dialog('close');
      } else{alert("Try another time"); }
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
        $('#bookingUpdatingDialog').dialog('close');
        refresh(bookingsState);
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
    $('#kids').empty();
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
 refresh(bookingsState);
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
   bookingsArray = [];
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
    if(result==""){
        alert("In the room doesn't have enough free places");
    }else{
        alert("You create new booking");
    }

  },
  error: function(){
    alert("Unfortunately could not create new BOOKING");
  }
});
}

function setStartTime(idBooking){
  alert(idBooking);  var idElement = "#"+idBooking;
  var inputData = {
    startTime: $(idElement).find('#arrivalTime').val(),
    id: idBooking,
  };
  $.ajax({
    url: "setTime",
    contentType: 'application/json',
    data:   JSON.stringify(inputData),
    type: 'POST',
    success: function(data){
      var obj = JSON.parse(data);
      var bookingTime = $(idElement).find('.bookingTime');
      bookingTime.empty();
      bookingTime.append(obj.startTime + " - " + obj.endTime);
      $(idElement).addClass('highlight-active');
    }});
}


function setEndTime(idBooking){

  var idElement = "#"+idBooking;
  var inputData = {
    endTime: $(idElement).find('#leaveTime').val(),
    id: idBooking,
  };

  $.ajax({
    url: "BookDuration",
    contentType: 'application/json',
    type: 'POST',
    data: JSON.stringify(inputData),
    success: function(data){
      var bookingObj = JSON.parse(data);
      if(bookingObj.durationLong>0){
        $.ajax({
         url: "setEndTime",
         contentType: 'application/json',
         data:   JSON.stringify(inputData),
         type: 'POST',
         success: function(data){
           var obj = JSON.parse(data);
           var bookingTime = $(idElement).find('.bookingTime');
           $(idElement).addClass('highlight-complet');
         }});
      }else{
        $('#invalidTimeModal').modal('show');
      }
    }
  });
}