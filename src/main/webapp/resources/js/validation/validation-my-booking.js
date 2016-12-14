function validateDate() {
        var startDate = $("#from").val();
        var endDate = $("#to").val();
        if (new Date(startDate) > new Date(endDate)) {
            $("#errorDate").html(messages.date.fromBiggerThanTo);
        } else {
            $("#errorDate").html("");
            return true;
        }
}

$('input[type="date"]').on('change', function(){

    var date = $(this).val();
    var regex = /^(\d{4})(\/|-)(\d{1,2})(\/|-)(\d{1,2})$/;

    if(date.match(regex) == null){
        var nowYear = new Date().getFullYear();
        var month = date.split("-")[1];
        var day = date.split("-")[2];
        $(this).val(nowYear + "-" + month + "-" + day);
    }
    else{
        validateDate();
    }
});
