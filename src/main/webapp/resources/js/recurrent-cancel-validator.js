
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
    '\\-((0?[1-9])|([1-2][0-9])|(30)))|(0?2\\-((0?[1-9])|(1[0-9])|(2[0-8]))))))$'].join('')
);
var ERROR_MESSAGES_RECURRENT_CANCEL = [];
var ELEMENT_FOR_ERROR_MESSAGES = '#validation-information';

/**
 * Checks if the startDate or the endDate is not null or undefined
 *
 * @param startDate start of the date for checking
 * @param endDate end of the day for checking
 * @returns {boolean} if startDate or endDate is null or undefined - returns false.
 * Otherwise  - returns true.
 */
function isNotEmptyDates(startDate, endDate) {
    var result = true;
    if (typeof startDate === 'undefined' || startDate === null){
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.emptyStartDate);
        result = false;
    }
    if (typeof endDate === 'undefined' || endDate === null){
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.emptyEndDate);
        result = false;
    }
    return result;
}

/**
 * Checks if the the startDate or the endDate is in the right format.
 * Right format is YYYY-M{1, 2}-d{1, 2}. Examples: 2016-09-28, 2016-9-28.
 * Method considers leap year, so the string 2016-02-29 is correct, but
 * 2015-02-29 is incorrect.
 *
 * @param startDate start of the date for checking
 * @param endDate end of the day for checking
 * @returns {boolean} true if the format is correct, otherwise - false.
 */
function isRightFormatDates(startDate, endDate) {
    var result = true;
    if (!DATE_REGEXP.test(startDate)) {
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.startDate);
        result = false;
    }
    if (!DATE_REGEXP.test(endDate)) {
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.endDate);
        result = false;
    }
    return result;
}

/**
 * Checks if the startDate is not before the event that is in the past
 * or is beginning yet. Also checks if the endDate is not before startDate.
 *
 * @param startDate start of the date for checking
 * @param endDate endDate end of the day for checking
 * @param recurrentId id of the recurrent event for checking
 * @returns {boolean} true if startDate is not before the event that is
 * in the past or is beginning yet. Otherwise - false.
 */
function isNotInThePast(startDate, endDate, recurrentId) {
    var result = true;
    var arStartEv;
    var i, event;
    for (i = 0; i < allEvents.length; i++)
        if (allEvents[i].recurrentId === recurrentId) {
            event = allEvents[i];
            break;
        }
    arStartEv = event.start.split('T');

    //We add arStartEv[1] (time of starting event), because startDate is only a date
    if (new Date(startDate + " " + arStartEv[1]) < new Date()) {
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.pastStartDay);
        result = false;
    }
    if (new Date(startDate) > new Date(endDate)) {
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.pastEndDay);
        result = false;
    }
    return result;
}

/**
 * Checks if the startDate is not before the start of the recurrent events
 * or if the endDate is not after the end of the recurrent events.
 *
 * @param recurrentId id of the recurrent event for checking
 * @param startDate start of the date for checking
 * @param endDate endDate end of the day for checking
 * @returns {boolean}
 */
function validateRecurrentDatesPeriod(recurrentId, startDate, endDate){
    var firstReccurentDate = MAX_POSSIBLE_DATE;
    var lastReccurentDate = MIN_POSSIBLE_DATE;
    var result = true;
    var startD, endD;
    $.each(allEvents, function(index, value) {
        startD = new Date(value.start);
        endD = new Date(value.end);
        if(value.recurrentId == recurrentId && startD < firstReccurentDate)
            firstReccurentDate = startD;
        if (value.recurrentId == recurrentId && endD > lastReccurentDate)
            lastReccurentDate = endD;
    });
    if (new Date(startDate + END_UTC_TIME_UNIT) < firstReccurentDate) {
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.wrongDateStartRange);
        result = false;
    }
    if (new Date(endDate + START_UTC_TIME_UNIT) > lastReccurentDate) {
        ERROR_MESSAGES_RECURRENT_CANCEL.push(messages.notCorrect.wrongDateEndRange);
        result = false;
    }
    return result;
}

/**
 * Normalizes the date format. If the date is in format YYYY-M-dd then
 * method returns YYYY-0M-dd. If the date is in format YYYY-MM-d then
 * method returns YYYY-MM-0d. If the date is in format YYYY-M-d then
 * method returns YYYY-0M-0d. Otherwise return initial date.
 *
 * @param date for normalize
 * @returns {string} normalized date
 */
function normalizeDate(date) {
    var result = date;
    var arDates;
    if (date.length < 10) {
        arDates = date.split("-");
        if (arDates[1].length < 2)
            arDates[1] = 0 + arDates[1];
        if (arDates[2].length < 2)
            arDates[2] = 0 + arDates[2];
        result = arDates[0] + "-" + arDates[1] + "-" + arDates[2];
    }
    return result;
}

/**
 * Prints all error messages to given HTML element.
 *
 * @param element element for printing messages
 */
function printErrorMessages(element) {
    $(ERROR_MESSAGES_RECURRENT_CANCEL).each(function(index, message) {
        $(element).append("- " + message + '</br>');
    });
}

/**
 * Checks if the startDate or the endDate is not null or undefined.
 * Checks if the the startDate or the endDate is in the right format.
 * Right format is YYYY-M{1, 2}-d{1, 2}. Checks if the startDate is not
 * before the event that is in the past or is beginning yet. Checks
 * if the endDate is not before startDate. Checks if the startDate is not
 * before the start of the recurrent events or if the endDate is not after
 * the end of the recurrent events.
 *
 * @param recurrentId id of the recurrent event for checking
 * @param startDate start of the date for checking
 * @param endDate end of the day for checking
 * @returns {boolean} true if all of the checks is true. Otherwise - false
 */
function validateRecurrentDates(recurrentId, startDate, endDate) {
    var result = true;
    var normStartDate, normEndDate;
    $(ELEMENT_FOR_ERROR_MESSAGES).html("");
    if (!isNotEmptyDates(startDate, endDate) || !isRightFormatDates(startDate, endDate)) {
        printErrorMessages(ELEMENT_FOR_ERROR_MESSAGES);
        ERROR_MESSAGES_RECURRENT_CANCEL = [];
        result = false;
    } else {
        normStartDate = normalizeDate(startDate);
        normEndDate = normalizeDate(endDate);
        if (!isNotInThePast(normStartDate, normEndDate, recurrentId)) {
            printErrorMessages(ELEMENT_FOR_ERROR_MESSAGES);
            ERROR_MESSAGES_RECURRENT_CANCEL = [];
            result = false;
        }
        if (!validateRecurrentDatesPeriod(recurrentId, normStartDate, normEndDate)) {
            printErrorMessages(ELEMENT_FOR_ERROR_MESSAGES);
            ERROR_MESSAGES_RECURRENT_CANCEL = [];
            result = false;
        }
    }
    return result;
}
