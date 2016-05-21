$(document).ready(function(){
    $(".btn.glyphicon").click(function(){
            var id = $(".kidscard").data("id");
            window.location.href = "editmykid?kidId=" + id;
        });
});