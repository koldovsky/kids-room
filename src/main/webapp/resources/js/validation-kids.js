$(document).ready(function() {
    $(function() {
        $('#kidregistrform').validate({
            rules: {
                firstName: {
                    required: true,
                    minlength: nameMinLength,
                    maxlength: nameMaxLength
                },
                lastName: {
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
                    required: messages.kid.requiredFirstName
                },
                lastName: {
                    required: messages.kid.requiredLastName
                }
            }
        });
    });
});
