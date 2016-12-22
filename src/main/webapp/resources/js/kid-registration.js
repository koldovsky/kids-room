$(document).ready(function(){
    $('#add-kid-cancel').click(function () {
        var kidredirect='/home/mykids';
        $(location).attr('href', kidredirect).reload(true);
    });
    document.getElementById('date').value = new Date().toDateInputValue();
});
