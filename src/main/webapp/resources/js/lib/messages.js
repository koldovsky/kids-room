/**
 * Created by shuk on 19.11.16.
 */
messages = {
	kid : {
		invalidFirstName : 'Invalid First Name',
		invalidLastName : 'Invalid Last Name',
		requiredFirstName: 'The field first name can not be empty', 
		requiredLastName: 'The field last name can not be empty',
		regexKidFirstName: 'To enter a first name, use only letters',
		regexKidLastName: 'To enter a last name use only letters',

		toShortFirstName : 'Please enter at least ' + nameMinLength + ' characters',
		toShortLastName : 'Please enter at least ' + nameMinLength + ' characters',
		toLongFirstName : 'Please enter less ' + nameMaxLength + ' than  characters',
		toLongLastName : 'Please enter less ' + nameMaxLength + ' than  characters',

		editFirstName : "Please enter at least 2 characters",
		editLastName : "Please enter at least 2 characters",
        editFirstNameWithDigits : "First name can not contain digits",
		editLastNameWithDigits : "Last name can not contain digits"

    },
    date: {
    	fromBiggerThanTo:  ' \'To\' date should be bigger than \'From\' date ',
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
			sortAscending:  ': activate to sort column ascending',
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

			dateInThePast: 'Date can\'t be in the past, current date is: ',
			timeInThePast: 'Start time can\'t be in the past, current time is: ',
			endTimeGreaterThanStartTime: 'End time must be at least one minute later than the start time',
			minimalDatesDifference: 'Recurrent: End date must be at least one day later than the start date',
			emptyTitle: 'Title can\'t be empty',
			noDaysSelected: 'Recurrent: At least one day must be selected',
			noKidsSelected: 'At least one kid must be selected',
			bookingTypeMismatchWhenUpdating: 'Can\'t convert weekly booking to single',
			incorrectData: 'Incorrect Data: ',

			dateInThePast: "Date can't be in the past, current date is: ",
			timeInThePast: "Start time can't be in the past, current time is: ",
			endTimeGreaterThanStartTime: "End time must be at least one minute later than the start time",
			minimalDatesDifference: "Recurrent: End date must be at least one day later than the start date",
			emptyTitle: "Title can't be empty",
			noDaysSelected: "Recurrent: At least one day must be selected",
			noKidsSelected: "At least one kid must be selected",
			bookingTypeMismatchWhenUpdating: "Can't convert weekly booking to single",
			incorrectData: "Incorrect Data: ",
            roomDeactivate: "Room cannot be inactive, when it has events and bookings that take place in the future. Please contact manager."

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
	adminValidation:{
		required: 'The field is empty. Fill in the field please.'
	}
};

