function validateDate() {
    var startDate = document.getElementById("from").value.toString().replace(/-/gi, "");
    var endDate = document.getElementById("to").value.toString().replace(/-/gi, "");
    if (endDate - startDate < 0) {
        document.getElementById("errorDate").innerHTML = "Початкова дата не повинна перевищувати кінцеву";
    } else {
        document.getElementById("errorDate").innerHTML = "";
    }
}
