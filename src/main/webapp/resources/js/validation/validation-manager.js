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
                   regEmail: emailRegex
                },
                firstName: {
                   required: true,
                   regexFirstName: nameRegex
                },
                lastName: {
                   required: true,
                   regexLastName: nameRegex
                },
                phoneNumber:{
                   required: true,
                   regexPhone: phoneRegex
                }
            }
        });
    });
});

jQuery.extend(jQuery.validator.messages, {
    required: messages.adminValidation.required
});