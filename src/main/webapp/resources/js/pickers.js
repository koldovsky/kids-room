$('.datepickers').datepicker({
    dateFormat: constants.parameters.dateFormat,
    setDate: moment().format(constants.parameters.dateFormatUpperCase)
});
$('.datepickers').attr("maxlength", constants.parameters.dateLength);
$('.datepickers').keyup(function () {
    var format = function () {
        if(moment(this.value, constants.parameters.dateFormatUpperCase, true).isValid()){
            $(this).removeClass("error");
        }
        else {
            $(this).addClass("error");
        }
    };

    $('.datepickers').keyup(format);
    $('.datepickers').change(format);
});
$('.datepickers').blur(function () {
    if(!moment(this.value, constants.parameters.dateFormatUpperCase, true).isValid()){
        $(this).val(moment().format(constants.parameters.dateFormatUpperCase));
        $(this).removeClass("error");
    }
});
$('.picker').timepicker({
    timeFormat: 'H:i',
    step: 1
});
$('.picker').attr("maxlength", constants.parameters.timeLength);
$('.picker').keypress(function(e) {
    if (e.which != constants.keyCodes.backspace && (e.which < constants.keyCodes.zero ||
            e.which > constants.keyCodes.colon)) {
        return false;
    } else {
        var format = function (e) {
            if (!constants.regex.twentyFourHoursRegex.test(this.value)) {
                $(this).css("color", "red");
            }
            else {
                $(this).css("color", "#555");
            }
        }

        $('.picker').keyup(format);
        $('.picker').change(format);
    }
});
