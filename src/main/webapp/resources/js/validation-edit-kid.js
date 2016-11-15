/**
 * Created by vova on 13.11.16.
 */
$(document).ready(function() {

    $(function() {
        $.validator.addMethod("regexKidFirstName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Invalid First Name");
        $.validator.addMethod("regexKidLastName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Invalid Last Name");


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