$(document).ready(function(){
    $(".kidslistitem").click(function(){
        var id = $(".kidslistitem").index(this);
        $(".kidinfo:eq("+id+")")
            .slideToggle(400, function(){
        });
    });
});