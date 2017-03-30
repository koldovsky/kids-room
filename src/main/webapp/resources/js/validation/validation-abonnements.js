$(function () {

    $.validator.addMethod("regexName", function (value, element, regexpr) {
        return regexpr.test(value);
    }, messages.room.errors.invalidName);
    $.validator.addMethod("requiredWithEmptySpace", function (value) {
        return value.trim().length != 0;
    }, messages.room.errors.requiredWithEmptySpace);

    var abonnementFormObj = {
        rules: {
            name: {
                required: true,
                minlength: constants.parameters.nameMinLength,
                maxlength: constants.parameters.nameMaxLength
            },
            price: {
                required: true,
                regexName: constants.regex.numbersOnly
            },
            hour: {
                required: true,
                regexName: constants.regex.numbersOnly,
                min: constants.parameters.abonnementsMinHour,
                max: constants.parameters.abonnementsMaxHour
            }
        }
    };

    $('#createAbonnementForm').validate(abonnementFormObj);
    $('#updateAbonnementForm').validate(abonnementFormObj);
});
