
function validateDate() {

    var fromDateValue = $('#from').val();
    var toDateValue = $('#to').val();
    var regex = /^(\d{4})(\/|-)(\d{1,2})(\/|-)(\d{1,2})$/;
    var valid = true;

    if (fromDateValue.match(regex) == null) {
        nowYear = new Date().getFullYear();
        month = fromDateValue.split("-")[1];
        day = fromDateValue.split("-")[2];
        $('#from').val(nowYear + "-" + month + "-" + day);
        valid = false;
    } else if (toDateValue.match(regex) == null) {
        nowYear = new Date().getFullYear();
        month = toDateValue.split("-")[1];
        day = toDateValue.split("-")[2];
        $('#to').val(nowYear + "-" + month + "-" + day);
        valid = false;
    }
    if((new Date(fromDateValue)) > (new Date(toDateValue))) {
        document.getElementById("dateError").
            innerHTML = "<br>`from date` cannot be greater than `to date`";
        valid = false;
    }else {
        document.getElementById("dateError").
            innerHTML = "";
    }

    return valid;
}
