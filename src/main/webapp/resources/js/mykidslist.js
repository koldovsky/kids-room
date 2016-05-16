$(document).ready(function(){
    $(".kidslistblock").click(function(){
        var id = $(".kidslistblock").index(this);
        $(".kidinfo:eq("+id+")")
            .slideToggle("slow", function(){
        });
    });


    $(".btn.glyphicon").click(function(){
         var position = $(".btn.glyphicon").index(this).toString();
         var id = $(".kidinfo:eq("+position+")").data("id");
         window.location.href = "editmykid?kidId=" + id;
    });
});