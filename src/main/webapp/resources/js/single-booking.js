$(document).ready(function(){
    $('.choose-booking').click(function() {
        if ($("input[id='weekly-booking']:checked").val()) {
            $('#recurrent-booking-end-date').prop("disabled", false)
        } else {
            if ($('#recurrent-booking-end-date').val() != $('#recurrent-booking-start-date').val()) {
                $('#recurrent-booking-end-date').val($('#recurrent-booking-start-date').val())
            };
            $('#recurrent-booking-start-date').change(function() {
                $('#recurrent-booking-end-date').val($(this).val())
            });
            $('#recurrent-booking-end-date').prop("disabled", true)
        }
    });
});