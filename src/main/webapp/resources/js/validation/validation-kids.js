$(document).ready(function() {
    $.validator.addMethod("regexName", function (value, element, regexpr) {
        return regexpr.test(value);
    }, messages.kid.invalidFirstName);
    $(function() {
        $('#kidregistrform').validate({
            rules: {
                firstName: {
                    regexName: nameRegex,
                    required: true,
                    minlength: nameMinLength,
                    maxlength: nameMaxLength
                },
                lastName: {
                    regexName: nameRegex,
                    required: true,
                    minlength: nameMinLength,
                    maxlength: nameMaxLength
                },
                comment: {
                    maxlength: commentMaxLength
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
