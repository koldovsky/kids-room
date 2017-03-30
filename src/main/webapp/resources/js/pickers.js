$('.datepickers').datepicker({
    dateFormat: constants.parameters.dateFormat,
    setDate: moment().format(constants.parameters.dateFormatUpperCase)
});
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
$('.picker').timepicker({
    timeFormat: 'H:i',
    step: 1
});
$('.picker').attr("maxlength", "5");
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
