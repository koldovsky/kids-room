
const messages = {
    kid: {
        invalidFirstName: 'Invalid First Name',
        invalidLastName: 'Invalid Last Name',
        requiredFirstName: 'The field first name can not be empty',
        requiredLastName: 'The field last name can not be empty',
        regexKidFirstName: 'Only letters are allowed here',
        regexKidLastName: 'Only letters are allowed here',

        toShortFirstName: 'Please enter at least ' + constants.parameters.nameMinLength + ' characters',
        toShortLastName: 'Please enter at least ' + constants.parameters.nameMinLength + ' characters',
        toLongFirstName: 'Please enter less ' + constants.parameters.nameMaxLength + ' than  characters',
        toLongLastName: 'Please enter less ' + constants.parameters.nameMaxLength + ' than  characters',

        editFirstName: 'Please enter at least 2 characters',
        editLastName: 'Please enter at least 2 characters',
        editFirstNameWithDigits: 'First name can not contain digits',
        editLastNameWithDigits: 'Last name can not contain digits'
    },
    date: {
        fromBiggerThanTo: ' \'To\' date should be bigger than \'From\' date ',
        toBiggerThanCurrent: '\'To\' date shouldn\'t be bigger than current date is',
        fromSmallerThanLast: '\'From\' date can\'t be smaller than 01.01.2017',
        wrongDate:'You are trying to enter not existing date',
        startEventDate: 'Start at: ',
        endEventDate: 'End at: ',
        description: 'Description: '
    },
    info: {
        description: 'Description: ',
        noDescription: 'This event hasn\'t any description'
    },
    dateTable: {
        emptyTable: 'No data available in table',
        processing: 'Processing...',
        search: 'Search:',
        lengthMenu: 'Show _MENU_ entries',
        info: 'Showing _START_ to _END_ of _TOTAL_ entries',
        infoEmpty: 'Showing 0 to 0 of 0 entries',
        infoFiltered: '(filtered from _MAX_ total entries)',
        loadingRecords: 'Loading...',
        zeroRecords: 'No matching records found',
        paginate: {
            first: 'First',
            previous: 'Previous',
            next: 'Next',
            last: 'Last'
        },
        aria: {
            sortAscending: ': activate to sort column ascending',
            sortDescending: ': activate to sort column descending'
        }
    },
    modal: {
        kid: {
            comment: 'Comment',
            choose: 'Please choose kid'
        }
    },
    event: {
        errors: {
            titleFieldRequired: 'Title can\'t be empty ',
            titleMaximumCharacters: 'Title should contain no more than' + constants.parameters.titleMaxLenght + ' characters',
            descriptionMaximumCharacters: 'Description should contain no more than ' + constants.parameters.descriptionMaxLenght + ' characters',
            startDateFormat: 'Start date is invalid or dosen\'t exist , use right format yyyy-mm-dd HH:MM',
            endDateFormat: 'End date is invalid or dosen\'t exist, use right format yyyy-mm-dd HH:MM',
            singleEventDateEquals:  'For one-day event dates must be the same',
            invalidColor: 'This color is invalid',
            invalidDate: 'Date is invalid',
            invalidTime: 'Time is invalid',
            dateInThePast: 'Date can\'t be in the past, current date is: ',
            timeInThePast: 'Start time can\'t be in the past, current time is: ',
            endTimeGreaterThanStartTime: 'End time must be at least one minute later than the start time',
            minimalDatesDifference: 'Recurrent: End date must be at least one day later than the start date',
            emptyTitle: 'Title can\'t be empty',
            noDaysSelected: 'Recurrent: At least one day must be selected',
            noKidsSelected: 'At least one kid must be selected',
            bookingTypeMismatchWhenUpdating: 'Can\'t convert weekly booking to single',
            eventTypeMismatchWhenUpdating: 'Type of event cannot be changed',
            incorrectData: 'Incorrect Data: ',
            roomDeactivate: 'Room cannot be inactive, when it has events and bookings that take place in the future. Please contact manager.',
            dateDoesntExits: 'Chosen date doesn\'t exist, please select valid date',
            cannotCreateEventsForNonExistingDates: 'For some dates events cannot be created as soon as dates do not exist.',
            noEligibleEventsForPeriod: 'There are no eligible events for this period of time',
        }
    },
    notCorrect: {
        time: '<b>unknown</b>. Entered not correct time. Acceptable format is <b>HH:mm</b>',
        startDate: 'Entered not correct start date. Acceptable format is <b>dd:MM:YYYY</b>',
        endDate: 'Entered not correct end date. Acceptable format is <b>dd:MM:YYYY</b>',
        server: '<b>unknown</b>. Server error',
        emptyStartDate: 'Not entered start date',
        emptyEndDate: 'Not entered end date',
        pastStartDay: 'The start day can\'t be in the past',
        pastEndDay: 'The end day can\'t be smaller than start Day',
        wrongDateStartRange: 'The start day is out of range of chosen events',
        wrongDateEndRange: 'The end day is out of range of chosen events'
    },
    adminValidation: {
        email: 'Email is invalid.',
        firstName: 'Last Name is invalid.',
        lastName: 'Last Name is invalid.',
        phone: 'Phone number is invalid. Example +380991234567',
        required: 'The field is empty. Fill in the field please.'
    },
    room: {
        errors: {
            invalidName: 'Invalid field Name',
            invalidAdress: 'Invalid field Adress',
            invalidCity: 'Invalid field City',
            invalidPhone: 'Invalid field Phone',
            requiredWithEmptySpace: 'This field must be required',
            roomIsFull: 'Room is full'
        },
        warnings: {
            active: 'Warning! Room has active bookings',
            planning: 'Warning! Room has planned bookings'
        }
    },
    booking: {
        hint: {
            leaveTime: 'Press to set leave time',
            arrivedTime: 'Press to set arrival time'
        }
    },
    preloader: {
        buttons: {
            add : 'Add'
        }
    }
};
