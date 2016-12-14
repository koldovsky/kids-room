var CREATE_RECURRENT_BOOKING_DIALOG_DAYS_OF_WEEKS = ['Monday-booking', 'Tuesday-booking', 'Wednesday-booking', 'Thursday-booking', 'Friday-booking', 'Saturday-booking'];
var CREATE_RECURRENT_BOOKING = "create-recurrent-booking";
var UPDATE_RECURRENT_BOOKING = "update-recurrent-booking";
var CREATE_SINGLE_BOOKING = "create-single-booking";
var UPDATE_SINGLE_BOOKING = "update-single-booking";
var SINGLE_BOOKING = "create-booking"
var CREATE_BOOKING_DIALOG_SINGLE_BOOKING_RADIOBUTTON = "no-recurrent-booking";



function validateSingleBookingUpdateDialogData(){
    var startDate = $.datepicker.parseDate("yy-mm-dd",$('#recurrent-booking-start-date').val());
    var startTime = $("#bookingUpdatingStartTimepicker").timepicker('getTime');
    var endTime = $("#bookingUpdatingEndTimepicker").timepicker('getTime');
    validateTime(startDate,null,startTime,endTime);
    return isValidationSuccessful();
}

function validateCreateBookingDialogData(bookingType){
    var startDate = $.datepicker.parseDate("yy-mm-dd",$('#recurrent-booking-start-date').val());
    var endDate = $.datepicker.parseDate("yy-mm-dd",$('#recurrent-booking-end-date').val());
    var startTime = $("#recurrent-booking-start-time").timepicker('getTime');
    var endTime = $("#recurrent-booking-end-time").timepicker('getTime');

    if(bookingType==CREATE_SINGLE_BOOKING){
        validateTime(startDate,null,startTime,endTime);
    }
    if(bookingType==CREATE_RECURRENT_BOOKING || bookingType==UPDATE_RECURRENT_BOOKING){
        validateTime(startDate,endDate,startTime,endTime);
        validateDaysOfWeekSelection(CREATE_RECURRENT_BOOKING_DIALOG_DAYS_OF_WEEKS);
    }

    if(bookingType!=UPDATE_RECURRENT_BOOKING){
        checkKidsSelection();
    }
    if(bookingType==UPDATE_RECURRENT_BOOKING){
        if (isRadioButtonSelected(CREATE_BOOKING_DIALOG_SINGLE_BOOKING_RADIOBUTTON)){
            dataValidationStrings.push(VALIDATION_ERRORS["bookingTypeMismatchWhenUpdating"]);
        }
    }
    return isValidationSuccessful();

}

function checkKidsSelection(){
    var numberOfSelectedKids = 0;
    for (var i = 0; i < ($('#number-of-kids').val()); i++) {
        kidsCommentId = $('#comment-' + i).val();
        if ($('#checkboxKid' + kidsCommentId).is(':checked')) {
            numberOfSelectedKids++;
        }
    }
    if(numberOfSelectedKids<1){
            dataValidationStrings.push(VALIDATION_ERRORS["noKidsSelected"]);
    }
}

