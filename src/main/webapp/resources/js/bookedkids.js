
function cancelBooking(idBook){
     var str = "cancelBook/"+idBook;
        $.ajax({
        url: str,
        success: function(result){
        var text = result;
        var bookings = JSON.parse(text);
        $('#' + bookings.id).hide();
     }});
  }


   function setStartTime(idBooking){

          var idElemant = "#"+idBooking;
          var inputData = {
              startTime: $(idElemant).find('#arrivalTime').val(),
              id: idBooking,

          };
              $.ajax({
                  url: "setTime",
                  contentType: 'application/json',
                  data:   JSON.stringify(inputData),
                  type: 'POST',
                  success: function(data){
                  var obj = JSON.parse(data);
                  var clear = "#"+idBooking+" .bookingTime"
                  $(clear).empty();
                  $(clear).append(obj.startTime + " - " + obj.endTime);
                  },
                  error: function(){

                  }});
                  }


    function setEndTime(idBooking){

          var idElement = "#"+idBooking;
          var inputData = {
              endTime: $(idElement).find('#leaveTime').val(),
              id: idBooking,

          };
              $.ajax({
                  url: "setEndTime",
                  contentType: 'application/json',
                  data:   JSON.stringify(inputData),
                  type: 'POST',
                  success: function(data){
                  var obj = JSON.parse(data);
                  var bookingTime = $(idElement).find('.bookingTime');
                  bookingTime.empty();
                  bookingTime.append(obj.startTime + " - " + obj.endTime);
                  $(idElement).addClass('highlighted');
                  },
                  error: function(){

              }});
          }




            $('.arrivalTime').on('click', function(){
                    var date = new Date().toString().match(/\d{2}:\d{2}/)[0];
                    var arrivalTime = $(this).val();
                    $(this).val(date);
            });


            $('.form-control').on('click', function(){
                 var date = new Date().toString().match(/\d{2}:\d{2}/)[0];
                 $(this).val(date);
                 });
