$(document).ready(function () {
    $.validator.addMethod("regexName", function (value, element, regexpr) {
        return regexpr.test(value);
    }, messages.room.errors.invalidName);
    $.validator.addMethod("regexAddress", function (value, element, regexpr) {
        return regexpr.test(value);
    }, messages.room.errors.invalidAdress);
    $.validator.addMethod("regexCity", function (value, element, regexpr) {
        return regexpr.test(value);
    }, messages.room.errors.invalidCity);
    $.validator.addMethod("regexPhone", function (value, element, regexpr) {
        return regexpr.test(value);
    }, messages.room.errors.invalidPhone);
    $.validator.addMethod("requiredWithEmptySpace", function (value) {
        return value.trim().length != 0;
    }, messages.room.errors.requiredWithEmptySpace);

    $('#roomForm').validate({
        rules: {
            name: {
                minlength: 1,
                maxlength: 255,
                requiredWithEmptySpace: true,
                regexName: constants.regex.nameRegex
            },
            address: {
                minlength: 1,
                maxlength: 255,
                requiredWithEmptySpace: true,
                regexAddress: constants.regex.addresssRegex
            },
            city: {
                minlength: 1,
                maxlength: 255,
                requiredWithEmptySpace: true,
                regexCity: constants.regex.nameRegex
            },
            phoneNumber: {
                minlength: 1,
                maxlength: 255,
                requiredWithEmptySpace: true,
                regexPhone: constants.regex.phoneRegex
            },
            capacity: {
                min: 1,
                max: 200,
                required: true
            },
            workingHoursStart: {
                required: true
            },
            workingHoursEnd: {
                required: true
            }
        }
    });
});
