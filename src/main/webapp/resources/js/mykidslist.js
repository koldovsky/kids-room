$(document).ready(function(){
    $(".kidslistitem").click(function(){
        var id = $(".kidslistitem").index(this);
        $(".kidinfo:eq("+id+")")
            .slideToggle(400, function(){
        });
    });


    $(".btn.glyphicon").click(function(){
         var id = $(".btn.glyphicon").index(this).toString();

         /*$.ajax({
            url:"editmykid",
            data: {
                kidPosition: id
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                                alert("Status: " + textStatus);
                                alert("Error: " + errorThrown);
                   }
         });*/
         window.location.href = "editmykid?kidPosition=" + id;
    });
});