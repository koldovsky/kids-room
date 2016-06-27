var bookingsArray = [];
var idBooking;
var dateNow = new Date();
var bookingsState=['BOOKED'];
var table=null;

$('#date-booking').val(dateNow.toISOString().substr(0,10));

$(function(){
  $('#date-booking').change(function(){
    ref(bookingsState);
  });
  $('.picker').timepicker({
      timeFormat: 'H:i',
      step: 15,
      minTime: '15:00',
      maxTime: '22:00'
  });
});

function selectRoomForManager(roomId) {
 ref(bookingsState);
}

function bookedBooking(){
    bookingsState= ['BOOKED'];
    ref(bookingsState);
}
function activeBooking(){
    bookingsState = ['ACTIVE'];
    ref(bookingsState);
}
function leavedBooking(){
    bookingsState = ['COMPLETED'];
    ref(bookingsState);
}
function allBooking(){
    bookingsState = ['ACTIVE', 'BOOKED', 'CALCULATE_SUM', 'COMPLETED'];
    ref(bookingsState);
}


function ref(bookingsState){
    var time = $('#date-booking').val();
    var idRoom = localStorage["roomId"];
    src = 'dailyBookings/'+time + "/" +idRoom + "/" +bookingsState;
  jQuery.extend({
      getValues: function() {
          var result = null;
          $.ajax({
              url: src,
              async: false,
              success: function(data) {
                  result = data;
              }
          });
         return result;
      }
  });
  var results = $.getValues();
  var data = JSON.parse(results);

  if(!(table===null)){
      table.destroy();
  }

  table = $('#example').DataTable( {
            "processing": true,
             "columnDefs": [ {
                    "searchable": false,
                    "orderable": false,
                    "targets": 0
                } ],
            "order": [[ 2, 'asc' ]],
            'data': data,
            'columns': [
                  { "data": null },
                  { "data": 'kidName',
                  "className": "kid-name",
                   "fnCreatedCell": function (nTd, sData, oData) {
                               $(nTd).html('<a href=profile?id=' + oData.idChild +'>'+oData.kidName+"</a>");
                           }
                   },
                  {"data": "startTime",
                  "className": "edit-button",
                    "fnCreatedCell": function (nTd, sData, oData) {
                         var td = '<span class="book-id" id =' + oData.id + '><span id="book-start-time">' + oData.startTime + '</span> - '
                         + '<span id="book-end-time">' + oData.endTime + '</span></span><button class="btn btn-sm glyphicon glyphicon-edit"></button>';
                         $(nTd).empty();
                         $(nTd).append(td);
                    }
                  },
                  { "className":      'arrivalTime',
                    "defaultContent":
                     '<input type="time" class="form-control" placeholder = "click here" >'
                     +'<button class="btn btn-sm btn-success glyphicon glyphicon-saved" id="arrival-btn" ></button>'
                    },
                  { "className":      'leaveTime',
                                   "defaultContent":
                                   '<input type="time" class="form-control">'
                                                        +'<button class="btn btn-sm btn-success glyphicon glyphicon-saved" id="leave-btn"></button>'
                  },

            ]
        });
         table.on( 'order.dt search.dt', function () {
                table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
                    cell.innerHTML = i+1;
                } );
            }).draw();
}


 $('#example tbody').on('click', '#arrival-btn',    function () {
            var tr = $(this).closest('tr');
            var id =  table.row(tr).data().id;
            var time = $(this).closest('td').find('input').val();
           setStartTime(id, time);
           ref(bookingsState);
    });
    $('#example tbody').on('focus', 'input', function () {
        var time = dateNow.toString().match(/\d{2}:\d{2}/)[0];
        $(this).val(time);
    });

     $('#example tbody').on('click', '#leave-btn',  function () {
                var tr = $(this).closest('tr');
                var id =  table.row(tr).data().id;
                var time = $(this).closest('td').find('input').val();
               setEndTime(id, time);
               ref(bookingsState);
     });
     $('#example tbody').on('click', '.edit-button',  function () {
            idBooking = $(this).closest('td').find('.book-id').attr('id');
            var kidsname = $(this).closest('tr').find('.kid-name').text();

          var date = $('#date-booking').val();
         var startTime = $(this).closest('td').find('#book-start-time').text();
          var endTime = $(this).closest('td').find('#book-end-time').text();
          $('#bookingUpdatingStartTimepicker').val(startTime);
          $('#bookingUpdatingEndTimepicker').val(endTime);
          $('#data-edit').val(date);
        $('#bookingUpdatingDialog').dialog();

     });



function setStartTime(id, startTime){
  var inputData = {
    startTime: startTime,
    id: id,
  };
  $.ajax({
    url: "setTime",
    contentType: 'application/json',
    data:   JSON.stringify(inputData),
    type: 'POST',
    success: function(data){
    }
  });
}

function setEndTime(id, time){
  var inputData = {
        endTime: time,
        id: id,
  };
  //if($(idElement).find('#arrivalTime').val()<$(idElement).find('#leaveTime').val()){
    $.ajax({
         url: "setEndTime",
         contentType: 'application/json',
         data:   JSON.stringify(inputData),
         type: 'POST',
         success: function(data){
         }
    });
  /*}else{
      $('#invalidTimeModal').modal('show');
  }*/
}


function createBookingss(){
  var date = $('#date-booking').val();
  $('#bookingStartDate').val(date);
  $('#bookingDialog').dialog();
}

$().ready(function() {
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















$().ready(function() {
  $('#deletingBooking').on('click', function(){
    $('#cancelModal').modal('show');
    $('#cancelButton').on('click', function(){
    cancelBooking();
    });
  });
  $('#updatingBooking').click(function() {
      var getData = $('#data-edit').val();
      var inputDate = {
          id: idBooking,
          startTime: getData +" " + $('#bookingUpdatingStartTimepicker').val(),
          endTime: getData +" " + $('#bookingUpdatingEndTimepicker').val(),
          roomId:  localStorage["roomId"],
      };
      updatingBooking(inputDate);
      $('#'+id).removeClass('highlight-active');
  });

   $('#selectUser').on('change', function(){
          $('#kids').empty();
          var idUser = $(this).val();
          var getKidsUrl = "get-kids/"+idUser;
          addKids(getKidsUrl);
      });
});


function addHilighted(bookings){

 $.each(bookings, function(index, value) {
  if(value.bookingState=="ACTIVE"){
    $(this).find('tr').addClass('highlight-active');

   // $('#'+value.id).find('#arrivalTime').val(value.startTime);
  }else if(value.bookingState=="COMPLETED"){
    $('#'+value.id).addClass('highlight-complet');
    $('#'+value.id).find('#arrivalTime').val(value.startTime);
    $('#'+value.id).find('#leaveTime').val(value.endTime);
  }
 });
}

function changeBooking(id){
  var date = $('#date-booking').val();
  var startTime = $('#' + id).find('#start-time').text();
  var endTime = $('#' + id).find('#end-time').text();
  $('#bookingUpdatingStartTimepicker').val(startTime);
  $('#bookingUpdatingEndTimepicker').val(endTime);
  $('#data-edit').val(date);
  $('#bookingUpdatingDialog').dialog();
  idBooking = id;
}

function createBooking(){
  var date = $('#date-booking').val();
  $('#bookingStartDate').val(date);
  $('#bookingDialog').dialog();
}

function cancelBooking(){
 var str = "cancelBook/"+idBooking;
    $.ajax({
      url: str,
      success: function(result){
        var text = result;
        var bookings = JSON.parse(text);
        $('#cancelModal').modal('hide');
        $('#bookingUpdatingDialog').dialog('close');
        ref(bookingsState);
      }
 });
}

function updatingBooking(inputDate) {
  $.ajax({
    url: 'change-booking',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(inputDate),
    success: function(data){
      if(data){
        ref(bookingsState);
        $('#updatingSuccess').modal('show');
        $('#bookingUpdatingDialog').dialog('close');
      } else{ $('#updatingInvalid').modal('show'); }
    }
  });
}

function addKids(getKidsUrl){
 $.ajax({
      url: getKidsUrl,
      success: function(result){
        var kids = JSON.parse(result);
        var kidsCount = kids.length;
        var tr="";
        $.each(kids, function(i, kid){
         tr+='<br><div>'
         +'<label><input type="checkbox"'
         + 'id="checkboxKid'+kid.id+'">'
         + kid.firstName
         + '<input type = "text"'+ 'placeholder="Comment"'
         + 'class="form-control"'
         + 'id="child-comment-' +kid.id+'">'
         +'</div>';
       });
        $('#kids-count').val(kidsCount);
        $('#kids').append(tr);
      }
    });
}

function Booking(startTime, endTime, comment, kidId, roomId, userId) {
  this.startTime = startTime;
  this.endTime = endTime;
  this.comment = comment;
  this.kidId = kidId;
  this.roomId = roomId;
  this.userId =  userId;
}

function sendBookingToServerForCreate(bookingsArray) {
 $.ajax({
  type: 'post',
  contentType: 'application/json',
  url: 'makenewbooking',
  dataType: 'json',
  data: JSON.stringify(bookingsArray),
  success: function (result) {
    if(result==""){
       $('#updatingInvalid').modal('show');
       $('#bookingDialog').dialog();
    }else{
        $('#createSuccess').modal('show');
        ref(bookingsState);
    }
  },
  error: function(){
    alert("Unfortunately could not create new BOOKING");
  }
});
}




