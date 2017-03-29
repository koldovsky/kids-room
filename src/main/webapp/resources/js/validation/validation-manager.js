$(document).ready(function() {
    $(function() {
       $.validator.addMethod('regEmail', function(value, element, regexpr) {
          return regexpr.test(value);
       }, messages.adminValidation.email);
       $.validator.addMethod('regexFirstName', function(value, element, regexpr) {
          return regexpr.test(value);
       }, messages.adminValidation.firstName);
       $.validator.addMethod('regexLastName', function(value, element, regexpr) {
          return regexpr.test(value);
       }, messages.adminValidation.lastName);
       $.validator.addMethod('regexPhone', function(value, element, regexpr) {
          return regexpr.test(value);
       }, messages.adminValidation.phone);
    $('#managerForm').validate({
            rules:{
                email: {
                    required: true,
                    regEmail: constants.regex.emailRegex
                },
                firstName: {
                    required: true,
                    regexFirstName: constants.regex.nameRegex,
                    minlength: constants.parameters.nameMinLength,
                    maxlength: constants.parameters.nameMaxLength
                },
                lastName: {
                    required: true,
                    regexLastName: constants.regex.nameRegex,
                    minlength: constants.parameters.nameMinLength,
                    maxlength: constants.parameters.nameMaxLength
                },
                phoneNumber:{
                    required: true,
                    regexPhone: constants.regex.phoneRegex
                }
            }
        });
        $('#managerUpdateForm').validate({
            rules:{
                email: {
                    required: true,
                    regEmail: constants.regex.emailRegex
                },
                firstName: {
                    required: true,
                    regexFirstName: constants.regex.nameRegex,
                    minlength: constants.parameters.nameMinLength,
                    maxlength: constants.parameters.nameMaxLength
                },
                lastName: {
                    required: true,
                    regexLastName: constants.regex.nameRegex,
                    minlength: constants.parameters.nameMinLength,
                    maxlength: constants.parameters.nameMaxLength
                },
                phoneNumber:{
                    required: true,
                    regexPhone: constants.regex.phoneRegex
                }
            }
        });
    });
});

jQuery.extend(jQuery.validator.messages, {
    required: messages.adminValidation.required,
    minlength: messages.adminValidation.toShortFirstName,
    maxlength: messages.adminValidation.toLongFirstName

});