$('.datepickers').datepicker({
    dateFormat: constants.parameters.dateFormat,
    setDate: moment().format(constants.parameters.dateFormatUpperCase)
});

