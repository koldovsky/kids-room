/**
 * Created by shuk on 13.01.17.
 */
var dataValidationStrings = [];

function validateUpdateSingleDialog() {
    var startDate = $.datepicker.parseDate("yy-mm-dd", $('#' + UPDATE_SINGLE_EVENT_DIALOG_START_DATE_ID).val()); // 535
    //var endDate = $.datepicker.parseDate("yy-mm-dd",$('#'+UPDATE_SINGLE_EVENT_DIALOG_END_DATE_ID).val()); // 535
    var startTime = $("#" + UPDATE_SINGLE_EVENT_DIALOG_START_TIME_ID).timepicker('getTime'); //538
    var endTime = $("#" + UPDATE_SINGLE_EVENT_DIALOG_END_TIME_ID).timepicker('getTime'); //550
    isTextInputElementEmpty(UPDATE_SINGLE_EVENT_DIALOG_INPUT_TITLE_ID);
    validateTime(startDate, null, startTime, endTime);
    return isValidationSuccessful();
}

function validateEventDialogData(eventType) {
    if (eventType == UPDATE_RECURRENT_EVENT || eventType == UPDATE_MONTHLY_EVENT) {
        if (isRadioButtonSelected(CREATE_EVENT_DIALOG_SINGLE_EVENT_RADIOBUTTON)) {
            dataValidationStrings.push(messages.event.errors.eventTypeMismatchWhenUpdating);
        }
    }
    var startDate = $.datepicker.parseDate("yy-mm-dd", $('#' + CREATE_EVENT_DIALOG_START_DATE_ID).val()); // 535
    var endDate = $.datepicker.parseDate("yy-mm-dd", $('#' + CREATE_EVENT_DIALOG_END_DATE_ID).val()); // 535
    var timeIsValid = true;
    if (startDate == null || endDate == null) {
        dataValidationStrings.push(messages.event.errors.dateDoesntExits);
        timeIsValid = false;
    }
    var startTime = $("#" + CREATE_EVENT_DIALOG_START_TIME_ID).timepicker('getTime'); //538
    var endTime = $("#" + CREATE_EVENT_DIALOG_END_TIME_ID).timepicker('getTime'); //550
    isTextInputElementEmpty(CREATE_EVENT_DIALOG_INPUT_TITLE_ID);
    if (timeIsValid) {
        if (eventType == CREATE_SINGLE_EVENT) {
            validateTime(startDate, null, startTime, endTime);
        } else {
            validateTime(startDate, endDate, startTime, endTime);
        }
    }
    if (eventType == CREATE_RECURRENT_EVENT || eventType == UPDATE_RECURRENT_EVENT) {
        validateDaysOfWeekSelection(CREATE_EVENT_DIALOG_DAYS_OF_WEEK);
    }

    if (eventType == CREATE_MONTHLY_EVENT || eventType == UPDATE_MONTHLY_EVENT) {
        validateDaysOfMonthSelection();
    }

    return isValidationSuccessful();
}

function validateTime(startDate, endDate, startTime, endTime) {
    var currentDate = new Date();
    var dayLengthInMilliseconds = startTime.getHours() * 60 * 60 * 1000;
    if ((startDate.getTime() < currentDate.getTime())) {
        if (startDate.getDate() != currentDate.getDate()) {
            dataValidationStrings.push(messages.event.errors.dateInThePast + currentDate.toLocaleDateString());
        }
    }
    if (startDate.getDate() == currentDate.getDate())
        if (startTime.getTime() < currentDate.getTime()) {
            dataValidationStrings.push(messages.event.errors.timeInThePast + currentDate.toLocaleTimeString());
        }
    if (endTime.getTime() - startTime.getTime() < MINUTE_LENGTH_IN_MILLISECONDS) {
        dataValidationStrings.push(messages.event.errors.endTimeGreaterThanStartTime);
    }
    if (endDate != null) {
        if (endDate.getTime() - startDate.getTime() < dayLengthInMilliseconds) {
            dataValidationStrings.push(messages.event.errors.minimalDatesDifference);
        }
    }
}

function validateDaysOfWeekSelection(daysArray) {
    var numberOfSelectedDays = 0;
    daysArray.forEach(function (item) {
        if ($('#' + item).is(':checked')) {
            numberOfSelectedDays++;
        }
    });
    if (numberOfSelectedDays < 1) {
        dataValidationStrings.push(messages.event.errors.noDaysSelected);
    }
}

function validateDaysOfMonthSelection() {
    if ($('#monthly-days').find('.active').length < 1) {
        dataValidationStrings.push(VALIDATION_ERRORS["noDaysSelected"]);
    }
}

function isTextInputElementEmpty(inputElement) {
    if ($("#" + inputElement).val().trim() === "") {
        dataValidationStrings.push(messages.event.errors.emptyTitle);
    }
}

function isRadioButtonSelected(radioButton) {
    if ($("#" + radioButton).is(":checked")) {
        return true;
    }
    return false;
}

function printValidationInfo() {
    var text = messages.event.errors.incorrectData;
    for (var i = 0; i < dataValidationStrings.length; i++) {
        text += "<br/>- " + dataValidationStrings[i] + ".";
    }
    $(".data-validation-information-string").html(text);
}

function cleanValidationInfo(dataValidationStrings) {
    $(".data-validation-information-string").html("");
}

function isValidationSuccessful() {
    if (dataValidationStrings.length > 0) {
        printValidationInfo(dataValidationStrings);
        dataValidationStrings.length = 0;
        return false;
    } else {
        cleanValidationInfo();
        return true;
    }
}