
 $('#addKids').hide();
 $('#butAddKid').click(function(){
    $('#addKids').show();
});



 $('#reportTime').hide();

 $('.showTable').click(function(){

    $('#reportTime').show();
});

 function show(a){

    var str = "getCompan/"+a;
    $.ajax({
        url: str,
        success: function(result){
            var text = result;
            var obj = JSON.parse(text);
            document.getElementById("startTime").innerHTML = obj.startTime;
            $('#idBook').val(obj.id);
            $('#realTime').val(obj.reportTime);

        }
    });
}

    $('#ApplyBooking').click(function(){

        var inputData = {
            startTime: $('#realTime').val(),
            id: $('#idBook').val(),
        };
            $.ajax({
                url: "setTime",
                contentType: 'application/json',
                data:   JSON.stringify(inputData),
                type: 'POST',
                success: function(data){
                var text = data;
                var obj = JSON.parse(text);
                document.getElementById("startTime").innerHTML = obj.startTime;
                },
                error: function(){
                alert(status);
                }
                });
    });

