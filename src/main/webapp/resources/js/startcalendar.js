$(document).ready(function() {

/*
     var startPosition = document.location.href.indexOf('')+''.length;
     var id = document.location.href.substring(startPosition, document.location.href.length);
     */

    $.ajax({url: "getCompanies", success: function(result){

        var objects = [];
        result = result.split(',');

        var string = result[0];

        string = " " + string;

        var stringToArray = string.split(' ');

        objects[0] = {
            title : stringToArray[1],
            start : stringToArray[2],
            end : stringToArray[3]
        }



        for(var i = 1; i < result.length; i++) {
            var string = result[i];
            var stringToArray = string.split(' ');

            objects[i] = {
                title : stringToArray[1],
                start : stringToArray[2],
                end : stringToArray[3]
            }
        }

        var myOptions = {
            val1 : 'text1',
            val2 : 'text2'
        };
        var mySelect = $('#roomAddress');
        $.each(myOptions, function(val, text) {
            mySelect.append(
                $('<option></option>').val(val).html(text)
            );
        });



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
});
