$(document).ready(function(){
    $(".kidslistitem").click(function(){
        var id = $(".kidslistitem").index(this);
        $(".kidinfo:eq("+id+")")
            .slideToggle(400, function(){
        });
    });


    $(".btn.glyphicon").click(function(){
         var position = $(".btn.glyphicon").index(this).toString();
         var id = $(".kidinfo:eq("+position+")").data("id");
         window.location.href = "editmykid?kidId=" + id;
    });
});