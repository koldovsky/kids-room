
/**
 * Created by shuk on 13.11.16.
 */
$(document).ready(function() {

    $(function() {
        $.validator.addMethod("regexKidFirstName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Invalid First Name ");
        $.validator.addMethod("regexKidLastName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "Invalid Last Name");

        $('#kidregistrform').validate({
            rules: {
                firstName: {
                    required: true,
                    regexKidFirstName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя\s]+$/,
                    maxlength: 35

                },
                lastName: {
                    required: true,
                    regexKidLastName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя\s]+$/,
                    maxlength: 35

                }
            },
            messages: {
                firstName: {
                    required: 'The field first name can not be empty',
                    regexKidFirstName: 'To enter a first name, use only letters',


                },
                lastName: {
                    required: 'The field last name can not be empty',
                    regexKidLastName: 'To enter a last name use only letters'

                }

            }
        });
    });
});
