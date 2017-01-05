$(document).ready(function() {
    $.validator.addMethod("regexName", function (value, element, regexpr) {
        return regexpr.test(value);
    }, messages.kid.invalidFirstName);
    $(function() {
        $('#kidregistrform').validate({
            rules: {
                firstName: {
                    regexName: constants.regex.nameRegex,
                    required: true,
                    minlength: constants.parameters.nameMinLength,
                    maxlength: constants.parameters.nameMaxLength
                },
                lastName: {
                    regexName: constants.regex.nameRegex,
                    required: true,
                    minlength: constants.parameters.nameMinLength,
                    maxlength: constants.parameters.nameMaxLength
                },
                comment: {
                    maxlength: constants.parameters.commentMaxLength
                }
            },
            messages: {
                firstName: {
                    required: messages.kid.requiredFirstName,
                    regexName: messages.kid.invalidFirstName
                },
                lastName: {
                    required: messages.kid.requiredLastName,
                    regexName: messages.kid.invalidLastName
                }
            }
        });
    });
});
