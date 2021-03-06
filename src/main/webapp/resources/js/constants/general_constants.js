const constants = {
    regex: {
        nameRegex: /^[a-zA-Zа-яА-ЯЇїІіЄєҐґ`´ʼ’'\-\s]+$/,
        phoneRegex: /^(\+38|7|)\W*\d{7,13}\W*$/,
        phonePrefixRegex: /\+\d+/g,
        phoneNumbersAndPlus: /^\+?\d+$/,
        numbersOnly: /\d+/g,
        emailRegex: /^[_a-zA-Z0-9-]+@softserveinc.com$/,
        timeRegex: /\d{2}:\d{2}/,
        addresssRegex: /^[a-zA-ZАа-щА-ЩЬьЮюЯяЇїІіЄєҐґ0-9\s]+$/,
        twoNumberRegex: /([0-9]{2})([0-9]{2})/i,
        twentyFourHoursRegex: /^([01]?[0-9]|2[0-3]):[0-5][0-9]/,
        discountValueRegex: /^[1-9][0-9]?$|^100$/

    },
    room: {
        warnings: {
            active: 'active_bookings',
            planning: 'planning_bookings'
        },
        properties: {
            id: 0,
            isActive: 6
        }
    },
    parameters: {
        titleMaxLenght: 255,
        descriptionMaxLenght: 250,
        nameMinLength: 2,
        nameMaxLength: 35,
        commentMaxLength: 250,
        hoursInDay: 24,
        timeZone: 2,
        dateFormat: "yy-mm-dd",
        dateFormatUpperCase: "YYYY-MM-DD",
        abonnementsMaxHour: 10000,
        abonnementsMinHour: 0,
        dateLength: 10,
        timeLength: 5,
        minDate: '2017-01-01'
    },
    keyCodes: {
        backspace: 8,
        zero: 48,
        colon: 58
    }
};

