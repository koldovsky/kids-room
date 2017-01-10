function validateEmptyTime(startTime, endTime) {
    return startTime !== '' && endTime !== '';
}

function validateRoomTime(time) {
    var result = false;
    if(time != '' && time >= roomWorkingStartTime && time <= roomWorkingEndTime) {
        result = true;
    }
    return result;
}
