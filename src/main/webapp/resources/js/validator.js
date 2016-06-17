jQuery.validator.setDefaults({
  debug: true,
  success: "valid"
});

$(document).ready(function() {

    $('input').on('blur', function() {
        if ($("#bookingUpdatingForm").valid()) {
            $('#updatingBooking').prop('disabled', false);
            $('#updatingBooking').css('cursor', 'pointer');
        } else {
            $('#updatingBooking').prop('disabled', 'disabled');
            $('#updatingBooking').css('cursor', 'not-allowed');
        }
    });

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


