$(document).ready(function() {
    $(function() {
        $.validator.addMethod("regexKidFirstName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, messages.kid.invalidFirstName);
        $.validator.addMethod("regexKidLastName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, messages.kid.invalidLastName);
        $('#editkidform').validate({
            rules: {

                firstName: {
                    required: true,
                    regexKidFirstName: constants.regex.nameRegex,
                    minlength: constants.parameters.nameMinLength,
                    maxlength: constants.parameters.nameMaxLength
                },
                lastName: {
                    required: true,
                    regexKidLastName: constants.regex.nameRegex,
                    minlength: constants.parameters.nameMinLength,
                    maxlength: constants.parameters.nameMaxLength
                }
            },
            messages: {
                firstName: {
                    required: messages.kid.requiredFirstName,
                    regexKidFirstName : messages.kid.regexKidFirstName,
                    minlength: messages.kid.toShortFirstName,
                    maxlength: messages.kid.toLongFirstName
                },
                lastName: {
                    required: messages.kid.requiredLastName,
                    regexKidLastName : messages.kid.regexKidLastName,
                    minlength: messages.kid.toShortLastName,
                    maxlength: messages.kid.toLongLastName
                }
            }
        });
    });
});
