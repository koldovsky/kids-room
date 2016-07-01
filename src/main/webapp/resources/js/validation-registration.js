$(document).ready(function() {
    $(function() {
       $.validator.addMethod("regex", function(value, element, regexpr) {
         return regexpr.test(value);
       }, "Please enter email with softserveinc.com domain.");
       $.validator.addMethod("regexPhone", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "Please enter valid phone number. Example 098");
    $('#userform').validate({
            rules:{

                firstName: {
                   required: true
                },
                lastName: {
                   required: true
                },
                email: {
                   required: true,
                   //regex: /^(\w){1,60}[@][s]oft[s]erveinc[.]com$/
                },
                password: {
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
                   regexPhone: /[0-9]{10,14}/
                }
            }, messages: {
                firstName: {
                    required: "This field cannot be empty. Please enter your name"
                },
                lastName: {
                    required: "This field cannot be empty. Please enter your last name"
                },
                email: {
                    required: "This field cannot be empty. Please enter your email"
                },
                phoneNumber:{
                    required: "This field cannot be empty. Please enter your phone number"
                },
                password:{
                    required: "This field cannot be empty. Please enter your password"
                },
                confirm:{
                    required: "This field cannot be empty. Please enter your password",
                    equalTo: "This password does not match the password in ‘Password’ field. Please enter the same password"
                }
            }

        });
});


});