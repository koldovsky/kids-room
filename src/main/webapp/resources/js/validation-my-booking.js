function validateDate() {
    if(validateBookingsDate() == true) {
        var startDate = document.getElementById("from").value.toString();
        var endDate = document.getElementById("to").value.toString();
        if (new Date(endDate) > new Date(startDate)) {
            document.getElementById("errorDate").innerHTML = " 'To' date should be bigger than 'From' date ";
        } else {
            document.getElementById("errorDate").innerHTML = "";
        }
    }
}
