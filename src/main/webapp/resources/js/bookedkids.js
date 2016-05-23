$(document).ready(function(){

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
                  alert(data);
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
                  $(idElement).find('.bookingTime').empty();
                  $(idElement).find('.bookingTime').append(obj.startTime + " - " + obj.endTime);
                  $(idElement).find('.kidsName').toggleClass('highlighted');
                  },
                  error: function(){
                  alert(data);
              }});
          }

                      $('.leaveTime').timepicki({
                    		show_meridian:false,
                    		min_hour_value:0,
                    		max_hour_value:23,
                    		step_size_minutes:15,
                    		overflow_minutes:true,
                    		increase_direction:'up',
                    		disable_keyboard_mobile: true
                    		});

});