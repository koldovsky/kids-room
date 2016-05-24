
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

          var idElemant = "#"+idBooking+" .arrivalTime";
          var inputData = {
              startTime: $(idElemant).val(),
              id: idBooking,
              kidName: idElemant,
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
              endTime: $(idElement).find('.leaveTime').val(),
              id: idBooking,
              kidName: idElement,
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
                  $(idElement).find('.kidsName').addClass('highlighted');
                  },
                  error: function(){

              }});
          }


            var timer;
            var delay = 100;

            $('.arrivalTime').on('mouseenter', function(){
                    var date = new Date().toString().match(/\d{2}:\d{2}/)[0];
                    var arrivalTime = $(this).val();
                    $(this).val(date);
            });


            $('.leaveTime').on('mouseover', function(){
                 var date = new Date().toString().match(/\d{2}:\d{2}/)[0];
                 $(this).val(date);
                 });