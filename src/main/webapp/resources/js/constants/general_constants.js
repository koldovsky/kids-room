const nameMinLength = 2;
const nameMaxLength = 35;
const commentMaxLength = 250;

const nameRegex = /^[a-zA-Zа-яА-ЯЇїІіЄєҐґ`´ʼ’'\s]+$/;
const phoneRegex = /^\+(?:[0-9] ?){6,14}[0-9]$/;
const emailRegex = /^[_a-zA-Z0-9-]+(\.[_a-zA-Z0-9-]+)*@(([0-9]{1,3})|([a-zA-Z]{2,11})|(aero|coop|info|museum|name))+(\\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name)))*\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name))*$/;

