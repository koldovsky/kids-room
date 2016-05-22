$(document).ready(function(){
    $("#edit").click(function(){
            var id = $(".kidscard").data("id");
            window.location.href = "editmykid?kidId=" + id;
        });
});