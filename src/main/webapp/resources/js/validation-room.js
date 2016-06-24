
$(document).ready(function() {
    $('#roomForm').validate({
        rules:{
            name: {
                required: true,
            },
            address:{
                required: true,
            },
            city: {
                required: true,
            },
            phoneNumber:{
                required: true,
            },
            capacity: {
                min: 1,
                max: 200,
                required: true,
            },
            workingHoursStart:{
                required: true,
            },
            workingHoursEnd:{
                required: true,
            }
        }
    });
});