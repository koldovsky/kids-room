function validateDate() {

    var fromDateValue = $('#from').val();
    var toDateValue = $('#to').val();
    var regex = /^(\d{4})(\/|-)(\d{1,2})(\/|-)(\d{1,2})$/;
    var isValidate = true;

    if (fromDateValue.match(regex) == null) {
        nowYear = new Date().getFullYear();
        month = fromDateValue.split("-")[1];
        day = fromDateValue.split("-")[2];
        $('#from').val(nowYear + "-" + month + "-" + day);
        isValidate = false;
    } else if (toDateValue.match(regex) == null) {
        nowYear = new Date().getFullYear();
        month = toDateValue.split("-")[1];
        day = toDateValue.split("-")[2];
        $('#to').val(nowYear + "-" + month + "-" + day);
        isValidate = false;
    }
    if((new Date(fromDateValue)) > (new Date(toDateValue))) {
        document.getElementById("dateError").
            innerHTML = "<br>`from date` cannot be greater than `to date`";
        isValidate = false;
    }else {
        document.getElementById("dateError").
            innerHTML = "";
    }

    return isValidate;
}
