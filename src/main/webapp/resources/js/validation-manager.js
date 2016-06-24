$(document).ready(function() {
    $(function() {
       $.validator.addMethod("regex", function(value, element, regexpr) {
         return regexpr.test(value);
       }, "Please enter email with softserveinc.com domain.");
    $('#managerForm').validate({
            rules:{
                email: {
                   required: true
                },
                firstName: {
                   required: true
                },
                lastName: {
                   required: true,
                },
                phoneNumber:{
                   required: true,
                   phoneUK: true
                },
            },
        });
    });
});