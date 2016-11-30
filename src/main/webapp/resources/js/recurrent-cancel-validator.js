
/**
 * Created by Sviatoslav Hryb on 29-Nov-16.
 */

var MAX_POSSIBLE_DATE = new Date(884541340800000); //Sat, 01 Jan 30000 00:00:00 GMT
var MIN_POSSIBLE_DATE = new Date(0); //Thu, 01 Jan 1970 00:00:00 GMT
var START_UTC_TIME_UNIT = "T00:00:00";
var END_UTC_TIME_UNIT = "T23:59:59";
var DATE_REGEXP = new RegExp(['^((\\d{2}(([02468][048])|([13579][26]))',
    '\\-((((0?[13578])|(1[02]))\\-((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))',
    '\\-((0?[1-9])|([1-2][0-9])|(30)))|(0?2\\-((0?[1-9])|([1-2][0-9])))))',
    '|(\\d{2}(([02468][1235679])|([13579][01345789]))\\-((((0?[13578])|(1[02]))',
    '\\-((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))',
    '\\-((0?[1-9])|([1-2][0-9])|(30)))|(0?2\\-((0?[1-9])|(1[0-9])|(2[0-8]))))))$'].join['']
);
var ERROR_MESSAGES_RECURRENT_CANCEL = [];

/**
 *
 * @param startDate
 * @param endDate
 * @returns {boolean}
 */
function isNotEmptyDates(startDate, endDate) {
    var result = true;
    if (startDate === null || typeof startDate === 'undefined'){
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.emptyStartDate);
        result = false;
    }
    if (endDate === null || typeof endDate === 'undefined'){
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.emptyEndDate);
        result = false;
    }
    return result;
}

/**
 *
 * @param startDate
 * @param endDate
 * @returns {boolean}
 */
function isRightFormatDates(startDate, endDate) {
    var result = true;
    if(!DATE_REGEXP.test(startDate)) {
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.startDate);
        result = false;
    }
    if(!DATE_REGEXP.test(endDate)) {
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.endDate);
        result = false;
    }
    return result;
}

/**
 *
 * @param startDate
 * @param endDate
 * @param recurrentId
 * @returns {boolean}
 */
function isNotInThePast(startDate, endDate, recurrentId) {
    var result = true;
    var arStartEv;
    var i, event;
    for(i = 0; i < allEvents.length; i++)
        if(allEvents[i].recurrentId === recurrentId) {
            event = allEvents[i];
            break;
        }
    arStartEv = event.start.split('T');

    //We add arStartEv[1] (time of starting event), because startDate is only a date
    if(new Date(startDate + " " + arStartEv[1]) < new Date()) {
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.pastStartDay);
        result = false;
    }
    if(new Date(startDate) > new Date(endDate)) {
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.pastEndDay);
        result = false;
    }
    return result;
}

/**
 *
 * @param recurrentId
 * @param startDate
 * @param endDate
 * @returns {boolean}
 */
function validateRecurrentDatesPeriod(recurrentId, startDate, endDate){
    var firstReccurentDate = MAX_POSSIBLE_DATE;
    var lastReccurentDate = MIN_POSSIBLE_DATE;
    var result = true;
    $.each(allEvents, function(index, value) {
        var startD = new Date(value.start);
        var endD = new Date(value.end);
        if(value.recurrentId == recurrentId && startD < firstReccurentDate)
            firstReccurentDate = startD;
        if (value.recurrentId == recurrentId && endD > lastReccurentDate)
            lastReccurentDate = endD;
    });
    if(new Date(startDate + END_UTC_TIME_UNIT) < firstReccurentDate) {
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.wrongDateStartRange);
        result = false;
    }
    if(new Date(endDate + START_UTC_TIME_UNIT) > lastReccurentDate) {
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.wrongDateEndRange);
        result = false;
    }
    return result;
}

/**
 *
 * @param element
 */
function printErrorMessages(element) {
    $(element).html("");
    $(ERROR_MESSAGES_RECURRENT_CANCEL).each(function(index, message) {
        $(element).append("- " + message + '</br>');
    });
}

function validateRecurrentDates(recurrentId, startDate, endDate) {
    var result = true;
    var element = '#validation-information';
    if(!isNotEmptyDates(startDate, endDate)) {
        printErrorMessages(element);
        result = false;
    } else if(!isRightFormatDates(startDate, endDate)) {
        printErrorMessages(element);
        result = false;
    } else if(!isNotInThePast(startDate, endDate, recurrentId)) {
        printErrorMessages(element);
        result = false;
    } if(!validateRecurrentDatesPeriod(recurrentId, startDate, endDate)) {
        printErrorMessages(element);
        result = false;
    }
    ERROR_MESSAGES_RECURRENT_CANCEL = [];
    return result;
}
