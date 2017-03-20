$('.datepickers').datepicker({
    dateFormat: constants.parameters.dateFormat,
    setDate: moment().format(constants.parameters.dateFormatUpperCase)
});

$('.picker').keypress(function (e) {
    if (e.which != 8 && e.which != 0 && e.which != 58 && (e.which < 48 || e.which > 57)) {
        return false;
    }
});

$('.picker').timepicker({
    timeFormat: 'H:i',
    step: 1
});
