/*






$(function() {
    $('#basicExample').timepicker({
        dateFormat: "hh-mm-ss"
    });
});



function changeFunc(id) {

    $('#calendar').fullCalendar('destroy');
    $('#dialog').dialog({
        autoOpen: false,
        show: {
            effect: 'drop',
            duration: 500
        },
        hide: {
            effect: 'clip',
            duration: 500
        }
    })

    $('#updatingDialog').dialog({
        autoOpen: false,
        show: {
            effect: 'drop',
            duration: 500
        },
        hide: {
            effect: 'clip',
            duration: 500
        }
    })

    var path = "getCompanies/" + id;

    $.ajax({
        url: path, success: function (result) {

            if (result.length != 0) {
                var objects = [];
                result = result.split(',');

                result[0] = " " + result[0];

                for (var i = 0; i < result.length; i++) {
                    var string = result[i];
                    var stringToArray = string.split(' ');

                    objects[i] = {
                        id: parseInt(stringToArray[4]),
                        title: stringToArray[1],
                        start: stringToArray[2],
                        end: stringToArray[3]
                    }
                }

                rendering(objects);

            } else {
                $('#calendar').fullCalendar('destroy');

                var objects = [{
                    title: "1",
                    start: "1",
                    end: "1"
                }]
                rendering(objects);

            }
        }
    });
}

function rendering(objects) {

    $('#calendar').fullCalendar({

        eventClick: function(event) {
            $("#updatingDialog").dialog('open');

            $('#titleUpdating').val(event.start);
            $('#startDateUpdating').val(event.title);

            $('#endDateUpdating').val(event.end);

            $('#updating').click(function () {
                $('#updatingDialog').dialog('close');
            })
            return 0;
        },

        dayClick: function f(date, jsEvent, view) {

            var clickDate = date.format();

            var dateobj = new Date(date);

            $('#startDate').val("");
            $('#endDate').val();
            $('#title').val(clickDate);

            $("#dialog").dialog('open');

            $('#creating').click(function () {

/!*
                alert("date: " + date);
                alert("clickDate: " + clickDate);
                alert("dateobj: " + dateobj);
*!/

                var ev = {
                    title: $('#startDate').val(),
                    start: $('#title').val(),
                    end: $('#endDate').val()
                }

                $('#calendar').fullCalendar('renderEvent', ev, true);
                forSendingToServer(ev);

                $('#title').val("");
                $('#dialog').dialog('close');

            })
        },




        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultDate: $('#calendar').fullCalendar('getDate'),

        //selectable: true,
        selectHelper: true,
        select: function (start, end) {
            var title = prompt('Event Title:');
            var eventData;
            if (title) {
                eventData = {
                    title: title,
                    start: start,
                    end: end
                };

                $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
            }
            $('#calendar').fullCalendar('unselect');
        },
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        events: objects
    });
}


function forSendingToServer(event) {

    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'getnewevent',
        dataType: "json",
        data: JSON.stringify({
            name: event.title,
            startTime: event.start,
            endTime: event.end
        })
    })
}






/*


*!/


/*




/*/

$(function() {
    $('#basicExample').timepicker({
        dateFormat: "hh-mm-ss"
    });
});

$(function() {
    $('#ender').timepicker({
        dateFormat: "hh-mm-ss"
    });
});

/*
function changeFunc(id) {

    $('#calendar').fullCalendar('destroy');
    $('#dialog').dialog({
        autoOpen: false,
        show: {
            effect: 'drop',
            duration: 500
        },
        hide: {
            effect: 'clip',
            duration: 500
        }
    })

    var path = "getCompanies/" + id;

    $.ajax({
        url: path, success: function (result) {

            if (result.length != 0) {
                var objects = [];
                result = result.split(',');

                result[0] = " " + result[0];

                for (var i = 0; i < result.length; i++) {
                    var string = result[i];
                    var stringToArray = string.split(' ');

                    objects[i] = {
                        id: parseInt(stringToArray[4]),
                        title: stringToArray[1],
                        start: stringToArray[2],
                        end: stringToArray[3]
                    }
                }

                rendering(objects);

            } else {
                $('#calendar').fullCalendar('destroy');

                var objects = [{
                    title: "1",
                    start: "1",
                    end: "1"
                }]
                rendering(objects);

            }
        }
    });
}

function rendering(objects) {

    $('#calendar').fullCalendar({

        dayClick: function f(date, jsEvent, view) {

            var clickDate = date.format();

            $('#endDate').val(clickDate.substring(0,10));
            $('#title').val(clickDate.substring(0,10));

            $("#dialog").dialog('open');

            $('#creating').click(function () {

                var installedTime = $("#basicExample").timepicker('getTime');


                var timepickerMinutes = installedTime.getMinutes();
                var timepickerHours = installedTime.getHours();

                if(timepickerMinutes == 0) {
                    timepickerMinutes = "00";
                } else {
                    timepickerMinutes = "30";
                }

                if(timepickerHours < 10) {
                    timepickerHours = "0" + timepickerHours.toString();
                } else {
                    timepickerHours = timepickerHours.toString();
                }


                var superBuffer = "" + clickDate.substring(0,11) + timepickerHours + ":" +
                    timepickerMinutes + clickDate.substring(16);






                var installedTime1 = $("#ender").timepicker('getTime');


                var timepickerMinutes1 = installedTime1.getMinutes();
                var timepickerHours1 = installedTime1.getHours();

                if(timepickerMinutes1 == 0) {
                    timepickerMinutes1 = "00";
                } else {
                    timepickerMinutes1 = "30";
                }

                if(timepickerHours1 < 10) {
                    timepickerHours1 = "0" + timepickerHours1.toString();
                } else {
                    timepickerHours1 = timepickerHours1.toString();
                }


                var superBuffer1 = "" + clickDate.substring(0,11) + timepickerHours1 + ":" +
                    timepickerMinutes1 + clickDate.substring(16);



                var ev = {
                    title: $('#startDate').val(),
                    start: "2016-05-21T14:00:00",
                    end:  "2016-05-21T15:00:00"
                }


                $('#calendar').fullCalendar('renderEvent', ev, true);

                    forSendingToServer(ev);

                    $('#dialog').dialog('close');
return;
            })
        },


        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultDate: $('#calendar').fullCalendar('getDate'),

        //selectable: true,
        selectHelper: true,
        select: function (start, end) {
            var title = prompt('Event Title:');
            var eventData;
            if (title) {
                eventData = {
                    title: title,
                    start: start,
                    end: end
                };
                $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
            }
            $('#calendar').fullCalendar('unselect');
        },
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        events: objects
    });
}


function forSendingToServer(event) {

    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'getnewevent',
        dataType: "json",
        data: JSON.stringify({
            name: event.title,
            startTime: event.start,
            endTime: event.end
        })
    })
}
*/


/**
 * Created by dima- on 12.05.2016.
 */


function changeFunc(id) {

    var maxId = 0;

    $('#calendar').fullCalendar('destroy');
    $('#dialog').dialog({
        autoOpen: false,
        show: {
            effect: 'drop',
            duration: 500
        },
        hide: {
            effect: 'clip',
            duration: 500
        }
    })

    var path = "getCompanies/" + id;

    $.ajax({
        url: path, success: function (result) {

            if (result.length != 0) {
                var objects = [];
                result = result.split(',');

                result[0] = " " + result[0];

                for (var i = 0; i < result.length; i++) {
                    var string = result[i];
                    var stringToArray = string.split(' ');

                    objects[i] = {
                        id: parseInt(stringToArray[4]),
                        title: stringToArray[1],
                        start: stringToArray[2],
                        end: stringToArray[3]
                    }
                }

                rendering(objects, id);

            } else {
                $('#calendar').fullCalendar('destroy');

                var objects = [{
                    title: "1",
                    start: "1",
                    end: "1"
                }]
                rendering(objects, id);

            }
        }
    });
}

function rendering(objects ,roomID) {

    $('#calendar').fullCalendar({

        dayClick: function f(date, jsEvent, view) {

            var clickDate = date.format();

            $('#startDate').val(clickDate);



            $('#title').val('');
            $('#endDate').val('');


            $("#dialog").dialog('open');

            $('#creating').click(function () {




                var installedTime = $("#basicExample").timepicker('getTime');


                var timepickerMinutes = installedTime.getMinutes();
                var timepickerHours = installedTime.getHours();

                if(timepickerMinutes == 0) {
                    timepickerMinutes = "00";
                } else {
                    timepickerMinutes = "30";
                }

                if(timepickerHours < 10) {
                    timepickerHours = "0" + timepickerHours.toString();
                } else {
                    timepickerHours = timepickerHours.toString();
                }


                var superBuffer = "" + clickDate.substring(0,11) + timepickerHours + ":" +
                    timepickerMinutes + clickDate.substring(16);


                var installedTime1 = $("#ender").timepicker('getTime');


                var timepickerMinutes1 = installedTime1.getMinutes();
                var timepickerHours1 = installedTime1.getHours();

                if(timepickerMinutes1 == 0) {
                    timepickerMinutes1 = "00";
                } else {
                    timepickerMinutes1 = "30";
                }

                if(timepickerHours1 < 10) {
                    timepickerHours1 = "0" + timepickerHours1.toString();
                } else {
                    timepickerHours1 = timepickerHours1.toString();
                }


                var superBuffer1 = "" + clickDate.substring(0,11) + timepickerHours1 + ":" +
                    timepickerMinutes1 + clickDate.substring(16);
                $('#title').val('2016-05-21T12:30:00');
                $('#endDate').val('2016-05-21T15:30:00');
                                                                                //Відстій починається тута

                var ev = {
                    title: $('#startDate').val(),
                    start: $('#title').val(),
                    end:   $('#endDate').val()
                }

                $('#calendar').fullCalendar('renderEvent', ev, true);
                forSendingToServer(ev, roomID);

                $('#title').val("");
                $('#dialog').dialog('close');
            })
        },


        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultDate: $('#calendar').fullCalendar('getDate'),

        //selectable: true,
        selectHelper: true,
/*        select: function (start, end) {
            var title = prompt('Event Title:');
            var eventData;
            if (title) {
                eventData = {
                    title: title,
                    start: start,
                    end: end
                };
                $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
            }
            $('#calendar').fullCalendar('unselect');
        },*/
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        events: objects
    });
}


function forSendingToServer(event, roomID) {

    $.ajax({
        type: 'post',
        contentType: 'application/json',
        url: 'getnewevent',
        dataType: "json",
        data: JSON.stringify({
            name: event.title,
            startTime: event.start,
            endTime: event.end,
            roomId: roomID
        })
    })
}

