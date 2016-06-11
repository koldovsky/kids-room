   function cancelBooking(idBook){
        $('#'+idBook).addClass('highlightedRed');
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
                                 bookingTime.empty();
                                 bookingTime.append(obj.startTime + " - " + obj.endTime);
                                 $(idElement).addClass('highlight-complet');
                             }});
                    }else{
                        $('#invalidTimeModal').modal('show');
                    }
                }
          });
   }


   var dateNow = function(){
       var date = new Date().toString().match(/\d{2}:\d{2}/)[0];
       $(this).val(date);
   };
   $('.input-group').find('#leaveTime').on('click', dateNow);
   $('.input-group').find('#arrivalTime').on('click', dateNow);


   $.getJSON("listBook", function( list ){

       $.each(list, function(index, value) {
       if(value.bookingState=="ACTIVE"){
           $('#'+value.id).addClass('highlight-active');
           $('#'+value.id).find('#arrivalTime').val(value.startTime);
       }else if(value.bookingState=="COMPLETED"){
           $('#'+value.id).addClass('highlight-complet');
           $('#'+value.id).find('#arrivalTime').val(value.startTime);
           $('#'+value.id).find('#leaveTime').val(value.endTime);
        }});
   });

 $('#data-booking').on('change', function(){
        var time = $(this).val();

        var src = 'manager-edit-booking/'+time;
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
 });


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





