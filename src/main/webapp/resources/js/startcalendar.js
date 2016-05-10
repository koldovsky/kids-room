$(document).ready(function() {

    $.ajax({url: "getCompanies", success: function(result){

     //   alert(result);
        var objects = [];
        result = result.split(',');
        for(var i = 0; i < result.length; i++ ) {
            var string = result[i];
            var stringToArray = string.split(' ');
      //      alert(string);

            objects[i] = {
                title : stringToArray[1],
                start : stringToArray[2],
                end : stringToArray[3]
            }
        }



        $('#calendar').fullCalendar({


            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            defaultDate: '2016-05-12',

            selectable: true,
            selectHelper: true,
            select: function(start, end) {
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
            events : objects
            /*
             events: [
             {
             title: 'All Day Event',
             start: '2016-05-01'
             },
             {
             title: 'Long Event',
             start: '2016-05-07',
             end: '2016-05-10'

             },
             {
             id: 999,
             title: 'Repeating Event',
             start: '2016-05-09T16:00:00'
             },
             {
             id: 999,
             title: 'Repeating Event',
             start: '2016-05-16T16:00:00'
             },
             {
             title: 'Meeting',
             start: '2016-05-12T10:30:00',
             end: '2016-05-12T12:30:00'
             },
             {
             title: 'Lunch',
             start: '2016-05-12T12:00:00'
             },
             {
             title: 'Meeting',
             start: '2016-05-12T14:30:00'
             },
             {
             title: 'Happy Hour',
             start: '2016-05-12T17:30:00'
             },
             {
             title: 'Dinner',
             start: '2016-05-12T20:00:00'
             },
             {
             title: 'Birthday Party',
             start: '2016-05-13T07:00:00'
             },
             {
             title: 'Click for Google',
             url: 'http://google.com/',
             start: '2016-05-10T10:15:00'
             }
             ]
             */

        });


    }
    });


/*
    var events = $('events').val();
    console.log(events);


        var data = {}
        data["query"] = $("#query").val();

        $.ajax({
            type : "GET",
            contentType : "application/json",
            url : "/",
            data : JSON.stringify(data),
            dataType : 'json',
            timeout : 100000,
            success : function(data) {
                alert("SUCCESS:");
                display(data);
            },
            error : function(e) {
                alert("ERROR: ");
                display(e);
            },
            done : function(e) {
                console.log("DONE");
            }
        });

/*
    $.ajax({
        type: 'GET',
        url: '/',
        dataType: 'json',
        contentType: 'application/json',
        success: function (response) {


            //    events = Object.keys(response).map(function(k) { return response[k]});


        },
        error: function(data,status,er) {
            alert("ERRROR");
        }
    });
*/


});
