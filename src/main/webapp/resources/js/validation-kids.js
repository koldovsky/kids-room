
/**
 * Created by shuk on 13.11.16.
 */
$(document).ready(function() {

    $(function() {
        $.validator.addMethod("regexKidFirstName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, messages.kid.invalidFirstName);
        $.validator.addMethod("regexKidLastName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, messages.kid.invalidLastName);

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
                    required: messages.kid.requiredFirstName,
                    regexKidFirstName: messages.kid.regexKidFirstName
                },
                lastName: {
                    required: messages.kid.requiredLastName,
                    regexKidLastName: messages.kid.regexKidLastName

                }
            }
        });
    });
});
