$(document).ready(function(){
    $(".kidslistblock").click(function(){
        var id = $(this).data("id");
        window.location.href = "profile?id=" + id;
    });
});