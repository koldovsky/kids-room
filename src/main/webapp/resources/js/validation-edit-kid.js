$(document).ready(function() {
    $(function() {
        $.validator.addMethod("regexKidFirstName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "messages.kid.editFirstName");
        $.validator.addMethod("regexKidLastName", function(value, element, regexpr) {
            return regexpr.test(value);
        }, "messages.kid.editLastName");
        $('#editkidform').validate({
            rules: {

                firstName: {
                    required: true,
                    regexKidFirstName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя'\-\s]+$/,
                    minlength: 2
                },
                lastName: {
                    required: true,
                    regexKidLastName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя'\-\s]+$/,
                    minlength: 2
                }
            },
            messages: {
                firstName: {
                    required: messages.kid.requiredFirstName,
                    minlength: messages.kid.editFirstName,
                    regexKidFirstName: messages.kid.regexKidFirstName
                },
                lastName: {
                    required: messages.kid.requiredLastName,
                    minlength: messages.kid.editLastName,
                    regexKidLastName:messages.kid.regexKidLastName
                }
            }
        });
    });
});
