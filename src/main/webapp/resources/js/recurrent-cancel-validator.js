/**
 * Created by svhry on 29-Nov-16.
 */

var MAX_POSSIBLE_DATE = new Date(884541340800000); //Sat, 01 Jan 30000 00:00:00 GMT
var MIN_POSSIBLE_DATE = new Date(0); //Thu, 01 Jan 1970 00:00:00 GMT
var ERROR_MESSAGES = []

function validateRecurrentDatesPeriod(recurrentId, startDate, endDate){
    var startMillis =  new Date(startDate).getTime();
    var endMillis = new Date(endDate).getTime();
    var oneDayMillis = 24 * 60 * 60 * 1000;
    var i;
    var firstReccurentDate = MAX_POSSIBLE_DATE;
    var lastReccurentDate = MIN_POSSIBLE_DATE;
    $.each(allEvents, function(index, value) {
        var startTime = new Date(value.start);
        if(startTime < firstReccurentDate)
            firstReccurentDate = startTime;
        if (startTime > lastReccurentDate)
            lastReccurentDate = startTime;
    });
}
