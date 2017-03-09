function validateDate() {
  var startDate = new Date($("#from").val());
  var endDate = new Date($("#to").val());
  var currentDate = new Date();
  var lastPossibleDate = new Date("01.01.2017");
  if (startDate > endDate) {
    $("#errorDate").html(messages.date.fromBiggerThanTo);
  }else if(endDate>currentDate){
    $("#errorDate").html(messages.date.toBiggerThanCurrent);
  }else if(lastPossibleDate>startDate){
    $("#errorDate").html(messages.date.fromSmallerThanLast);
  }
  else {
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
