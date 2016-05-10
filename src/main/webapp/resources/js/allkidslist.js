$(function(){
    $.fn.editable.defaults.mode = 'inline';
    $.fn.editable.defaults.disabled = true;

    $(document).ready(function() {
        $('a[id^=\'kidname\']').editable({
               type: 'text',
               pk: 1,
               name: 'kidname',
               title: 'Enter Full Name'
        });
        $('a[id^=\'parentname\']').editable({
               type: 'text',
               pk: 1,
               name: 'parentname',
               title: 'Enter Full Name'
        });
        $('a[id^=\'kidphone\']').editable({
               type: 'text',
               pk: 1,
               name: 'kidphone',
               title: 'Enter Phone Number'
        });
        $('a[id^=\'kidcomment\']').editable({
               type: 'textarea',
               pk: 1,
               name: 'kidcomment',
               title: 'Enter Comment'
        });
    });

    $('#enable').click(function() {
           $('#allkidstable .editable').editable('toggleDisabled');
     });
});
