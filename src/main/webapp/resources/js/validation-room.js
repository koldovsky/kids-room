
$(document).ready(function() {
    $(function() {
       $.validator.addMethod("regexCity", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "Please enter valid phone number. Example 0123456789");
        $('#roomForm').validate({
            rules:{
                name: {
                    required: true
                },
                address:{
                    required: true
                    regexCity: /^\w\S*$"/
                },
                city: {
                    required: true
                },
                phoneNumber:{
                    required: true
                },
                capacity: {
                    min: 1,
                    max: 200,
                    required: true
                },
                workingHoursStart:{
                    required: true
                },
                workingHoursEnd:{
                    required: true
                }
            }
        });
    });
});