/*
$(document).ready(function () {


    $.ajax({
        type: 'GET',
        url: 'index',
        dataType: 'json',
        contentType: 'application/json',
        success: function (response) {


        //    events = Object.keys(response).map(function(k) { return response[k]});


        },
        error: function(data,status,er) {
            alert("ERRROR");
        }
    });
});