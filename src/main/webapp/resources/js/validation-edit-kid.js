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
                    regexKidFirstName: nameRegex,
                    minlength: nameMinLength,
                    maxlength: nameMaxLength
                },
                lastName: {
                    required: true,
                    regexKidLastName: nameRegex,
                    minlength: nameMinLength,
                    maxlength: nameMaxLength
                }
            },
            messages: {
                firstName: {
                    required: messages.kid.requiredFirstName,
                    minlength: messages.kid.toShortFirstName,
                    maxlength: messages.kid.toLongFirstName
                },
                lastName: {
                    required: messages.kid.requiredLastName,
                    minlength: messages.kid.toShortLastName,
                    maxlength: messages.kid.toLongLastName
                }
            }
        });
    });
});
