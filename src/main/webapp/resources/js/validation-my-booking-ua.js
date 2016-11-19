function validateDate() {
    if(validateBookingsDate()) {
        var startDate = document.getElementById("from").value.toString();
        var endDate = document.getElementById("to").value.toString();
        if (new Date(endDate) > new Date(startDate)) {
            document.getElementById("errorDate").innerHTML = "Початкова дата не повинна перевищувати кінцеву";
        } else {
            document.getElementById("errorDate").innerHTML = "";
        }
    }
}

