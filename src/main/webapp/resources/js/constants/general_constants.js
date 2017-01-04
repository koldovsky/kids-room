const constants = {
    regex: {
        nameRegex : /^[a-zA-Zа-яА-ЯЇїІіЄєҐґ`´ʼ’'\-\s]+$/,
        phoneRegex : /^\+(?:[0-9] ?){6,14}[0-9]$/,
        emailRegex : /^[_a-zA-Z0-9-]+(\.[_a-zA-Z0-9-]+)*@(([0-9]{1,3})|([a-zA-Z]{2,11})|(aero|coop|info|museum|name))+(\\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name)))*\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name))*$/
    },
    parameters: {
        nameMinLength : 2,
        nameMaxLength : 35,
        commentMaxLength : 250,
        hoursInDay : 24,
        timeZone : 2
    }
}

