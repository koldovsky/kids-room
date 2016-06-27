$(document).ready(function() {
    $(function() {
       $.validator.addMethod("regEmail", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "Please enter a valid email address.");
       $.validator.addMethod("regexPhone", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "Please enter valid phone number. Example 0123456789");
    $('#managerForm').validate({
            rules:{
                email: {
                   required: true,
                   regEmail: /^[_a-zA-Z0-9-]+(\.[_a-zA-Z0-9-]+)*@(([0-9]{1,3})|([a-zA-Z]{2,11})|(aero|coop|info|museum|name))+(\\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name)))*\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name))*$/
                },
                firstName: {
                   required: true
                },
                lastName: {
                   required: true
                },
                phoneNumber:{
                   required: true,
                   phoneUK: true,
                   regexPhone: /[0-9]{10,14}/
                },
            },
        });
    });
});