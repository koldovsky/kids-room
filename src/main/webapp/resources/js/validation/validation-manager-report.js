function validateDate() {

  var startDate = new Date($("#startDate").val());
  var endDate = new Date($("#endDate").val());
  var currentDate = new Date();
  var lastPossibleDate = new Date("01.01.2017");

  if (isNaN(startDate.getTime())) {
    $("#errorDate").html(messages.date.wrongDate);
    $("#startDate").val(returnDateValue(lastPossibleDate));
    return false;
  }

  if (isNaN(endDate.getTime())) {
    $("#errorDate").html(messages.date.wrongDate);
    $("#endDate").val(returnDateValue(currentDate));
    return false;
  }

  if (startDate > endDate) {
    $("#errorDate").html(messages.date.fromBiggerThanTo);
    return false;
  } else if (endDate > currentDate) {
    $("#errorDate").html(messages.date.toBiggerThanCurrent);
    return false;
  } else if (lastPossibleDate > startDate) {
    $("#errorDate").html(messages.date.fromSmallerThanLast);
    return false;
  }
  else {
    $("#errorDate").html("");
    return true;
  }
}

$('input[type="date"]').on('change', function () {

  var date = $(this).val();
  var regex = /^(\d{4})(\/|-)(\d{1,2})(\/|-)(\d{1,2})$/;
  if (date.match(regex) == null || isNaN(Date.parse(date))) {
    var nowYear = new Date().getFullYear();
    var month = date.split("-")[1];
    var day = date.split("-")[2];
    $(this).val(nowYear + "-" + month + "-" + day);
  } else {
    validateDate();
  }
});

function returnDateValue(currentDate) {
  var day = ("0" + currentDate.getDate()).slice(-2);
  var month = ("0" + (currentDate.getMonth() + 1)).slice(-2);
  return currentDate.getFullYear() + "-" + (month) + "-" + (day);
}