$(document).ready(function(){
    $('#edit').click(function(){
            var id = $('.kidscard').data('id');
            window.location.href = "editmykid?kidId=" + id;
        });

    $('#file-upload').click(function(){
        if(!$('#image-msg').is(":visible")){
            $('#image-msg').toggle();
        }
    });

    $('#file-upload').change(function(){
        $('#image-msg').toggle();
        $('#file-submit').click();
    });
});