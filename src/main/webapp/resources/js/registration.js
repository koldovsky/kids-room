$(document).ready(function() {
    $(function() {
       $.validator.addMethod("regex", function(value, element, regexpr) {
         return regexpr.test(value);
       }, "Please enter email with softserveinc.com domain.");
    $('#userform').validate({
            rules:{
                lastName: {
                   required: true
                },
                firstName: {
                   required: true
                },
                email: {
                   required: true,
                   regex: /^(\w){1,60}[@][s]oft[s]erveinc[.]com$/
                },
                password:{
                   required: true,
                   minlength: 8
                },
                confirm:{
                   required: true,
                   minlength: 8,
                   equalTo: "#userPassword"
                },
                phoneNumber:{
                   required: true,
                   phoneUK: true
                }
            },

        });
        });


});