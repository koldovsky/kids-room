$(document).ready(function() {
    $(function() {
        $.validator.addMethod("regexKidFirstName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Incorrect first name. Please enter at least 2 characters");
        $.validator.addMethod("regexKidLastName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Incorrect last name. Please enter at least 2 characters");
        $('#editkidform').validate({
            rules: {

                firstName: {
                    required: true,
                    regexKidFirstName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя\s]+$/,
                    minlength: 2
                },
                lastName: {
                    required: true,
                    regexKidLastName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя\s]+$/,
                    minlength: 2
                }
            },
            messages: {
                firstName: {
                    required: 'Please enter at least 2 characters'
                },
                lastName: {
                    required: 'Please enter at least 2 characters'
                }
            }
        });
    });

});
