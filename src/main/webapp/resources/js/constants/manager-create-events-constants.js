//================================= Validation Part =============================
// EVENT type constants
var CREATE_RECURRENT_EVENT = "create-recurrent-event";
var CREATE_MONTHLY_EVENT = "create-monthly-event";
var UPDATE_RECURRENT_EVENT = "update-recurrent-event";
var UPDATE_MONTHLY_EVENT = "update-monthly-event";
var CREATE_SINGLE_EVENT = "create-single-event";
var UPDATE_SINGLE_EVENT = "update-single-event";
var CREATE_EVENT = "create-event";

// ID of elements of the CreateEventDialog
var CREATE_EVENT_DIALOG_NAME = "dialog";
var CREATE_EVENT_DIALOG_START_DATE_ID = "start-date-picker";
var CREATE_EVENT_DIALOG_END_DATE_ID = "end-date-picker";
var CREATE_EVENT_DIALOG_START_TIME_ID = "start-time-picker";
var CREATE_EVENT_DIALOG_END_TIME_ID = "end-time-picker";
var MINUTE_LENGTH_IN_MILLISECONDS = 60000;
var CREATE_EVENT_DIALOG_SINGLE_EVENT_RADIOBUTTON = "single-event-radio-button";
var CREATE_EVENT_DIALOG_WEEKLY_EVENT_RADIOBUTTON = "weekly-radio-button";
var CREATE_EVENT_DIALOG_MONTHLY_EVENT_RADIOBUTTON = "monthly-radio-button";
var CREATE_EVENT_DIALOG_DAYS_OF_WEEK = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
var CREATE_EVENT_DIALOG_INPUT_TITLE_ID = "event-title";
var CREATE_EVENT_DIALOG_INPUT_COLOR_ID = "color-select";
var CREATE_EVENT_DIALOG_INPUT_DESCRIPTION_ID = "description";

var UPDATE_SINGLE_EVENT_DIALOG_START_DATE_ID = "startDayUpdate";
var UPDATE_SINGLE_EVENT_DIALOG_END_DATE_ID = "endDateUpdate";
var UPDATE_SINGLE_EVENT_DIALOG_START_TIME_ID = "startTimeUpdate";
var UPDATE_SINGLE_EVENT_DIALOG_END_TIME_ID = "endTimeUpdate";
var UPDATE_SINGLE_EVENT_DIALOG_INPUT_TITLE_ID = "titleUpdate";
var UPDATE_SINGLE_EVENT_DIALOG_COLOR_ID ="color-select-single-event";
var UPDATE_SINGLE_EVENT_DIALOG_DESCRIPTION_ID="descriptionUpdate";
var ERROR_FIELD="-error";
var GENERAL_ERROR_FIELD="data-validation-information-string";

var VALIDATION_ERRORS = {
    "dateInThePast": "Date can't be in the past, current date is: ",
    "timeInThePast": "Start time can't be in the past, current time is: ",
    "endTimeGreaterThanStartTime": "End time must be at least one minute later than the start time",
    "minimalDatesDifference": "Recurrent: End date must be at least one day later than the start date",
    "emptyTitle": "Title can't be empty",
    "noDaysSelected": "Recurrent: At least one day must be selected",
    "noKidsSelected": "At least one kid must be selected",
    "bookingTypeMismatchWhenUpdating": "Can't convert weekly booking to single"
};



