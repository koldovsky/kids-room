   function cancelBooking(idBook){

        $('#cancelModal').find('#closeCencel').click(function(){
              $('#'+idBook).removeClass('highlightedRed');
        });

        $('#cancelModal').find('#cancelButton').click(function(){
              var str = "cancelBook/"+idBook;
              $.ajax({
                  url: str,
                  success: function(result){
                  var text = result;
                  var bookings = JSON.parse(text);
                  $('#' + bookings.id).hide();
              }});
              $('#cancelModal').modal('hide');
        });
   }


   function setStartTime(idBooking){

          var idElement = "#"+idBooking;
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
                  $(idElement).addClass('highlight-complet');
                  },
                  error: function(){
              }});
          }


            var dateNow = function(){
                 var date = new Date().toString().match(/\d{2}:\d{2}/)[0];
                 $(this).val(date);
                 };
           $('.input-group').find('#leaveTime').on('click', dateNow);
           $('.input-group').find('#arrivalTime').on('click', dateNow);


       $.getJSON("listBook", function( list ){
            $.each(list, function( index, value ) {
            if(value.bookingState=="ACTIVE"){
                $('#'+value.id).addClass('highlight-active');
            }else if(value.bookingState=="COMPLETED"){
                $('#'+value.id).addClass('highlight-complet');
            }});
       });











