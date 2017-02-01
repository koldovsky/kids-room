var generalErrorMessages = [];

/**
 * Checks if the title field not empty and has right lenght
 *
 * @param field to be checked
 *
 */
function validateTitle(field) {
    var fieldText = $("#" + field).val();
    if (fieldText.trim().length == 0) {
        generalErrorMessages.push(messages.event.errors.titleFieldRequired);
    }
    if (fieldText.length > constants.parameters.titleMaxLenght) {
        generalErrorMessages.push(messages.event.errors.titleMaximumCharacters);
    }
}

/**
 * Checks if the color Field contains true color code
 *
 * @param field to be checked
 *
 */
function validateColor(field) {
    var arrayOfColors = [];
    var colorSelector = $("#" + field);
    var colorCode = colorSelector.val();
    var isCorrectColor = false;
    colorSelector.find("option").each(function () {
        arrayOfColors.push($(this).val());
    });
    for (var i = 0; i < arrayOfColors.length; i++) {
        if (colorCode == arrayOfColors[i]) {
            isCorrectColor = true;
            break;
        }
    }
    if (!isCorrectColor) {
        generalErrorMessages.push(messages.event.errors.invalidColor);
    }
}

/**
 * Checks if the date Field contains valid Pattern
 *
 * @param field to be checked
 * @returns {boolean} when the field has invalid pattern - returns false
 * Otherwise - returns true.
 */
function isDateValid(field) {
    var date = $("#" + field).val();
    return DATE_REGEXP.test(date);

}

/**
 * Checks if the time Field contains valid Pattern
 *
 * @param field to be checked
 * @returns {boolean} when the field has invalid pattern - returns false
 * Otherwise - returns true.
 */
function isTimeValid(field) {
    var time = $("#" + field).val();
    var TIME_REGEXP = new RegExp('^([0-1][0-9]|[2][0-3]):([0-5][0-9])$');
    return TIME_REGEXP.test(time);

}

/**
 * Checks if description Field exceed {descriptionMaxLenght} characters
 *
 * @param field to be checked
 *
 */
function validateDescription(field) {
    var description = $("#" + field).val();
    if (description.length > constants.parameters.descriptionMaxLenght) {
        generalErrorMessages.push(messages.event.errors.descriptionMaximumCharacters);
    }
}

/**
 * Checks if two date are correct when event create in single type
 *
 * @param startDateField to be checked
 * @param endDateField to be checked
 * @param startTimeField to be checked
 * @param endTimeField to be checked
 *
 */
function validateSingleDate(startDateField, endDateField, startTimeField, endTimeField) {
    var startDateSelector = $("#" + startDateField);
    var endDateSelector = $("#" + endDateField);
    if (!isDateValid(startDateField) || !isTimeValid(startTimeField)) {
        generalErrorMessages.push(messages.event.errors.startDateFormat);
    } else if (!isDateValid(endDateField) || !isTimeValid(endTimeField)) {
        generalErrorMessages.push(messages.event.errors.endDateFormat);
    } else {
        var startDate = new Date(startDateSelector.val());
        var endDate = new Date(endDateSelector.val());
        var startTime = new Date(startDateSelector.val() + " " + $('#' + startTimeField).val());
        var endTime = new Date(endDateSelector.val() + " " + $('#' + endTimeField).val());
        if (startDate.getTime() != endDate.getTime()) {
            generalErrorMessages.push(messages.event.errors.singleEventDateEquals);
        }
        if ((startTime.getHours() > endTime.getHours())) {
            generalErrorMessages.push(messages.event.errors.endTimeGreaterThanStartTime);
        } else if ((startTime.getHours() == endTime.getHours()) && (startTime.getMinutes() >= endTime.getMinutes())) {
            generalErrorMessages.push(messages.event.errors.endTimeGreaterThanStartTime);
        }
    }
}

/**
 * Checks if two date are correct when event create in recurrent type
 *
 * @param startDateField to be checked
 * @param endDateField to be checked
 * @param startTimeField to be checked
 * @param endTimeField to be checked
 *
 */
function validateRecurrentDate(startDateField, endDateField, startTimeField, endTimeField) {
    var startDateSelector = $("#" + startDateField);
    var endDateSelector = $("#" + endDateField);
    if (!isDateValid(startDateField) || !isTimeValid(startTimeField)) {
        generalErrorMessages.push(messages.event.errors.startDateFormat);
    } else if (!isDateValid(endDateField) || !isTimeValid(endTimeField)) {
        generalErrorMessages.push(messages.event.errors.endDateFormat);
    } else {
        var startDate = new Date(startDateSelector.val());
        var endDate = new Date(endDateSelector.val());
        var startTime = new Date(startDateSelector.val() + " " + $('#' + startTimeField).val());
        var endTime = new Date(endDateSelector.val() + " " + $('#' + endTimeField).val());
        if (endDate <= startDate) {
            generalErrorMessages.push(messages.event.errors.minimalDatesDifference);
        }
        if ((startTime.getHours() > endTime.getHours())) {
            generalErrorMessages.push(messages.event.errors.endTimeGreaterThanStartTime);
        } else if ((startTime.getHours() == endTime.getHours()) && (startTime.getMinutes() >= endTime.getMinutes())) {
            generalErrorMessages.push(messages.event.errors.endTimeGreaterThanStartTime);
        }
    }
}

/**
 * Checks if at least one day selected
 *
 * @param arrayOfDays contain information about selected day
 * @returns {boolean} when the none day selected - returns false
 * Otherwise - returns true
 */
function validateRecurrentDaySelected(arrayOfDays) {
    var numberOfSelectedDays = 0;
    arrayOfDays.forEach(function (item) {
        if ($('#' + item).is(':checked')) {
            numberOfSelectedDays++;
        }
    });
    if (numberOfSelectedDays < 1) {
        generalErrorMessages.push(messages.event.errors.noDaysSelected);
    }
}

/**
 * Checks if the date is current or future
 *
 * @param dateField to be checked
 * @param timeField to be checked
 *
 */
function validateDateNotPast(dateField, timeField) {
    var fieldDate = new Date($('#' + dateField).val() + " " + $('#' + timeField).val());
    var currentDate = new Date();
    if (currentDate > fieldDate) {
        generalErrorMessages.push(messages.event.errors.dateInThePast + " " + currentDate);
    }
}
function isRadioButtonChecked(radioButton) {
    return $("#" + radioButton).is(":checked");

}

function validateEventType(eventType) {
    if ((eventType == UPDATE_RECURRENT_EVENT) &&
        (isRadioButtonChecked(CREATE_EVENT_DIALOG_SINGLE_EVENT_RADIOBUTTON))) {
        generalErrorMessages.push(messages.event.errors.eventTypeMismatchWhenUpdating);
    }
}

function printGeneralMessage(fieldError) {
    var errorMessage = messages.event.errors.incorrectData;
    for (var i = 0; i < generalErrorMessages.length; i++) {
        errorMessage += "<br>\* " + generalErrorMessages[i] + "\.";
    }
    $("." + fieldError).html(errorMessage);
}

function cleanGeneralValidationInfo(fieldError) {
    generalErrorMessages = [];
    $("." + fieldError).html("");
}

function isSingleUpdateFormValid() {
    validateTitle(UPDATE_SINGLE_EVENT_DIALOG_INPUT_TITLE_ID);
    validateColor(UPDATE_SINGLE_EVENT_DIALOG_COLOR_ID);
    validateDescription(UPDATE_SINGLE_EVENT_DIALOG_DESCRIPTION_ID);
    validateDateNotPast(UPDATE_SINGLE_EVENT_DIALOG_START_DATE_ID, UPDATE_SINGLE_EVENT_DIALOG_START_TIME_ID);
    validateSingleDate(UPDATE_SINGLE_EVENT_DIALOG_START_DATE_ID, UPDATE_SINGLE_EVENT_DIALOG_END_DATE_ID,
        UPDATE_SINGLE_EVENT_DIALOG_START_TIME_ID, UPDATE_SINGLE_EVENT_DIALOG_END_TIME_ID);
    return generalErrorMessages.length === 0;

}


function isCreateEventFormValid(eventType) {
    validateTitle(CREATE_EVENT_DIALOG_INPUT_TITLE_ID);
    validateColor(CREATE_EVENT_DIALOG_INPUT_COLOR_ID);
    validateDescription(CREATE_EVENT_DIALOG_INPUT_DESCRIPTION_ID);
    validateDateNotPast(CREATE_EVENT_DIALOG_START_DATE_ID, CREATE_EVENT_DIALOG_START_TIME_ID);
    if ((eventType == CREATE_RECURRENT_EVENT) || (eventType == UPDATE_RECURRENT_EVENT)) {
        validateRecurrentDate(CREATE_EVENT_DIALOG_START_DATE_ID, CREATE_EVENT_DIALOG_END_DATE_ID,
            CREATE_EVENT_DIALOG_START_TIME_ID, CREATE_EVENT_DIALOG_END_TIME_ID);
        validateRecurrentDaySelected(CREATE_EVENT_DIALOG_DAYS_OF_WEEK);
        if (eventType == UPDATE_RECURRENT_EVENT) {
            validateEventType(UPDATE_RECURRENT_EVENT);
        }
    } else if (eventType == CREATE_SINGLE_EVENT) {
        validateSingleDate(CREATE_EVENT_DIALOG_START_DATE_ID, CREATE_EVENT_DIALOG_END_DATE_ID,
            CREATE_EVENT_DIALOG_START_TIME_ID, CREATE_EVENT_DIALOG_END_TIME_ID);
    }
    return generalErrorMessages.length === 0;
}






