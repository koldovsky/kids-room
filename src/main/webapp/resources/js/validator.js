

$(document).ready(function() {
    $('#data-edit').on('change', validateUpdateElement);
    $('#bookingUpdatingStartTimepicker').on('change', validateUpdateElement);
    $('#bookingUpdatingEndTimepicker').on('change', validateUpdateElement);
});

function validateUpdateElement(){
    if ($("#bookingUpdatingForm").valid()) {
        $('#updatingBooking').prop('disabled', false);
        $('#updatingBooking').css('cursor', 'pointer');
    } else {
        $('#updatingBooking').prop('disabled', 'disabled');
        $('#updatingBooking').css('cursor', 'not-allowed');
    }
}

/*$(document).ready(function() {
    $('input').on('change', function(){
        if ($("#bookings").valid()) {
             $('#booking').prop('disabled', false);
             $('#booking').css('cursor', 'pointer');
        } else{
             $('#booking').prop('disabled', 'disabled');
             $('#booking').css('cursor', 'not-allowed')
        }
    });*/
    $('#bookingUpdatingForm').validate({
        rules:{
            date: {
                required: true,
                date: true
            },
            start: {
               required: true,
               time: true
            },
            end: {
                required: true,
                time: true
            }
        }
    });

});

    $('#bookings').validate({
        rules:{
               started: {
                  required: true,
                  time: true
               },
               ended: {
                   required: true,
                   time: true
               }

           }
    });




