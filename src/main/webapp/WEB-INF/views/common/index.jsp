<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link href='resources/css/fullcalendar.css' rel='stylesheet'/>
<link href='resources/css/fullcalendar.print.css' rel='stylesheet' media='print'/>


<script src='resources/js/moment.min.js'></script>

<script src='resources/js/jquery.min.js'></script>
<script src='resources/js/fullcalendar.js'></script>
<script src='resources/js/header-manager.js'></script>
<script src='resources/js/header-user.js'></script>

<script src='resources/js/userCalendar.js'></script>

<script src='resources/js/renderCalendar.js'></script>


<link href='resources/css/formForCalendar.css' rel='stylesheet'/>


<script type="text/javascript" src="resources/js/jquery.timepicker.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/jquery.timepicker.css"/>

<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">


<body>

<sec:authorize access="hasRole('USER')">


    <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="bookingUpdatingDialog" class="dialog" hidden>
                    <form id="bookingUpdatingForm">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <label for="bookingUpdatingStartDate">Start date</label>
                            <br>
                            <div class="col-xs-6  ">
                                <input type="text" class="form-control" id="bookingUpdatingStartDate"
                                       placeholder="startDate"
                                       readonly>
                            </div>
                            <div class="col-xs-6   ">
                                <input id="bookingUpdatingStartTimepicker" type="text"
                                       class="text-center time form-control timepicker"
                                       size="6"/>
                            </div>
                        </div>
                        <br>


                        <div class="form-group">
                            <label for="bookingUpdatingEndDate">End date</label>
                            <br>
                            <div class="col-xs-6  ">
                                <input type="text" class="form-control" id="bookingUpdatingEndDate"
                                       placeholder="endDate"
                                       readonly>
                            </div>
                            <div class="col-xs-6  ">
                                <input id="bookingUpdatingEndTimepicker" type="text"
                                       class="text-center time form-control timepicker pull-right"
                                       size="6"/>
                            </div>
                        </div>
                        <br>
                        <textarea class="col-xs-12" type="text" id="child-comment-update"></textarea>
                        <button type="button" class="btn btn-success" id="updatingBooking">Save</button>
                        <button type="button" class="btn btn-danger pull-right" id="deletingBooking">Cancel</button>

                        <button type="button" class="btn btn-xs btn-warning col-xs-12" id="deleting-single-booking">
                            Delete this
                            booking
                        </button>

                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="make-recurrent-booking" class="dialog" hidden>
                    <form id="recurrent-booking-form">
                        <!--  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> -->


                        <div class="form-group">
                            <label for="recurrent-booking-start-date">Start date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="date" class="text-center form-control" id="recurrent-booking-start-date"
                                       placeholder="startDate">
                            </div>
                            <div class="col-xs-6">
                                <input id="recurrent-booking-start-time" type="text"
                                       class="text-center time form-control timepicker" size="6"/>
                            </div>
                        </div>
                        <br>

                        <div class="form-group">
                            <label for="recurrent-booking-end-date">End date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="date" class="text-center form-control" id="recurrent-booking-end-date"
                                       placeholder="endDate">
                            </div>
                            <div class="col-xs-6">
                                <input id="recurrent-booking-end-time" type="text" class="text-center time form-control timepicker"
                                       size="6"/>
                            </div>
                        </div>


                        <div class="col-xs-12">
                            <div class="row">
                                <form role="form">
                                    <div class="row col-xs-4">
                                        <br>
                                        <div class="radio-button">
                                            <label><input type="radio" name="optradio-bookingform"
                                                          id="no-recurrent-booking"
                                                          class="booking-radio" checked>Single event</label>
                                        </div>
                                        <div class="radio-button">
                                            <label><input type="radio" name="optradio-bookingform" id="weekly-booking"
                                                          class="booking-radio">Weekly</label>
                                        </div>
                                    </div>
                                    <div class="col-xs-8 pull-right" id="days-for-recurrent-booking-form" hidden>
                                        <table class="table" id="days-for-recurrent-booking">
                                            <br>
                                            <thead>Check required days</thead>
                                            <tbody>
                                            <tr>
                                                <td><label><input type="checkbox" id="Monday-booking" value="Mon"
                                                                  class="day"> Monday</label><br>
                                                </td>
                                                <td><label><input type="checkbox" id="Tuesday-booking" value="Tue"
                                                                  class="day"> Tuesday</label><br>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label><input type="checkbox" id="Wednesday-booking" value="Wed"
                                                                  class="day"> Wednesday</label><br>
                                                </td>
                                                <td><label><input type="checkbox" id="Thursday-booking" value="Thu"
                                                                  class="day"> Thursday</label><br>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label><input type="checkbox" id="Friday-booking" value="Fri"
                                                                  class="day"> Friday</label><br>
                                                </td>
                                                <td><label><input type="checkbox" id="Saturday-booking" value="Sat"
                                                                  class="day"> Saturday</label><br>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </form>

                                <div class="clearfix"></div>
                                <div id="child-selector">
                                    <br>Select children for bookings:<br>
                                    <table>
                                        <c:forEach items="${kids}" var="kids" varStatus="loop">

                                            <c:set var="kidsArray" value="${kids}"/>

                                            <label><input type="checkbox" value=""
                                                          id="checkboxKid${kids.id}">${kids.firstName}</label>
                                            &nbsp;
                                        </c:forEach>
                                        <br>
                                        <c:forEach items="${kids}" var="kids" varStatus="loop">
                                            <tr>
                                                <span for="child-comment-${kids.id}" id="child-comment-${kids.id}-1"
                                                      hidden>Comment
                                                    for ${kids.firstName}:</span>

                                                <textarea type="text" class="col-xs-12" id="child-comment-${kids.id}"
                                                          hidden></textarea>

                                                <input type="text" id="comment-${loop.index}" value="${kids.id}" hidden>
                                                <br>
                                            </tr>
                                        </c:forEach>
                                        <input id="number-of-kids" hidden value="${fn:length(kids)}">
                                    </table>


                                    <div id="comment-for-one-child-updating" hidden>
                                        <textarea class="form-control" id="comment-for-update-recurrency"></textarea>
                                    </div>
                                </div>


                                <br>
                                <div class="clearfix"></div>
                                <div class="col-xs-3">
                                    <button type="button" class="btn btn-success" id="update-recurrent-booking"
                                            hidden="true">Update
                                    </button>
                                    <button type="button" class="btn btn-success live" id="book">Book</button>
                                </div>
                                <div class="col-xs-9 ">
                                    <button type="button" class="btn btn-danger pull-right" id="cancel-changes">Cancel
                                    </button>
                                </div>

                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="recurrent-change" class="dialog" hidden>
                    <form id="choose-updating-booking-form">

                        <div class="radio-button">
                            <label><input type="radio" id="single-update-booking" name="radio-check" checked>Just this
                                one</label>
                        </div>
                        <div class="radio-button">
                            <label><input type="radio" id="recurrent-update-booking" name="radio-check">The entire
                                series</label>
                        </div>

                        <button type="button" class="btn btn-success" id="confirm-choose-booking">Ok</button>
                        <button type="button" class="btn btn-danger pull-right" id="close-choose">Cancel</button>

                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="error" id="error-dialog"></div>
    <div class="container">


        <button type="button" class="btn btn-success" id="create-new-booking">Make booking</button>


        <button type="button" class="btn btn-success pull-right" data-toggle="modal"
                data-target=".bs-modal-lg-colourInfo">
            &nbsp; <span class="glyphicon glyphicon-info-sign"></span>&nbsp;
        </button>

        <div class="modal fade bs-modal-lg-colourInfo" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">

                    <div class="modal-body">

                        <div align="center">

                            <br>
                            <h2  ><span class="glyphicon glyphicon-stop"></span> - means that</h2>
                            <h2  ><span class="glyphicon glyphicon-stop"></span> - means this  </h2>
                            <h2  ><span class="glyphicon glyphicon-stop"></span> - means this  </h2>
                            <h2  ><span class="glyphicon glyphicon-stop"></span> - means this  </h2>
                            <h2  ><span class="glyphicon glyphicon-stop"></span> - means this  </h2>
                            <h2  ><span class="glyphicon glyphicon-stop"></span> - means this  </h2>
                            <br>
                            <span>SoftServe Inc</span>


                        </div>
                    </div>
                </div>
            </div>
        </div>

        <button type="button" class="btn btn-success pull-right" data-toggle="modal"
                data-target=".bs-modal-lg-contact">
            Contact
        </button>

        <div class="modal fade bs-modal-lg-contact" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">

                    <div class="modal-body">

                        <div align="center">

                            <br>
                            <H3 id="roomPhone"></H3>
                            <h4 id="showRoomManagers"></h4>
                            <span>SoftServe Inc</span>


                        </div>
                    </div>
                </div>
            </div>
        </div>


    </div>


    <div class="container">
        <div id='user-calendar'></div>
    </div>

</sec:authorize>

<sec:authorize access="hasRole('MANAGER')">


    <c:forEach items="${managersRoom}" var="r">

        ${r.id}

    </c:forEach>


    <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="dialog-recurrently" class="modal-dialog-recurrently dialog" hidden>
                    <form id="form-dialog-recurrently">

                        <div class="form-group">
                            <label for="recurrent-event-title">Event title</label>
                            <input type="text" class="form-control" id="recurrent-event-title" placeholder="title">
                        </div>

                        <div class="form-group">
                            <label for="recurrent-event-start-date">Start date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="date" class="form-control" id="recurrent-event-start-date"
                                       placeholder="startDate">
                            </div>
                            <div class="col-xs-5">
                                <input id="recurrent-event-start-time" type="text" class="time form-control timepicker"
                                       size="6"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="recurrent-event-end-date">End date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="date" class="form-control" id="recurrent-event-end-date"
                                       placeholder="endDate">
                            </div>
                            <div class="col-xs-5">
                                <input id="recurrent-event-end-time" type="text" class="time form-control timepicker"
                                       size="6"/>
                            </div>
                        </div>

                        <br><br>
                            <%--fefe



                            <table class="table">
                                <thead>Check required days</thead>
                                <tbody>
                                <tr>
                                    <td><label><input type="checkbox" id="Monday" value="Mon" class="day">Monday </label><br></td>
                                    <td><label><input type="checkbox" id="Tuesday" value="Tue" class="day">Tuesday</label><br></td>
                                </tr>
                                <tr>
                                    <td><label><input type="checkbox" id="Wednesday" value="Wed" class="day">Wednesday </label><br>
                                    </td>
                                    <td><label><input type="checkbox" id="Thursday" value="Thu" class="day">Thursday</label><br></td>
                                </tr>
                                <tr>
                                    <td><label><input type="checkbox" id="Friday" value="Fri" class="day">Friday </label><br></td>
                                    <td><label><input type="checkbox" id="Saturday" value="Sat" class="day">Saturday</label><br></td>
                                </tr>
                                </tbody>
                            </table>
    --%>

                        <label for="recurrent-event-description">Description</label>
                        <textarea class="form-control" id="recurrent-event-description"
                                  placeholder="description" cols="30"></textarea>

                        <div class="col-xs-6">
                            <button type="button" class="btn btn-success" id="recurrent-event-create">Create</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="dialog" class="dialog" hidden>
                    <form id="form">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <label for="startDate">Event title</label>
                            <input type="text" class="form-control" id="startDate" placeholder="title">
                        </div>
                        <div class="form-group">
                            <label for="title">Choose a color</label>
                            <select id="color-select">
                                <option value="#ff0000" style="background:red">red</option>
                                <option value="#ffff00" style="background:yellow">yellow</option>
                                <option value="#800080" style="background:purple">purple</option>
                                <option value="#0000ff" style="background:blue">blue</option>
                                <option value="#6AA4C1" style="background:#6AA4C1" selected="selected">light blue
                                </option>
                                <option value="#808080" style="background:grey">grey</option>
                                <option value="#008000" style="background:green">green</option>
                                <option value="#ffa500" style="background:orange">orange</option>
                                <option value="#00ffff" style="background:aqua">aqua</option>
                                <option value="#ffd700" style="background:gold">gold<br></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="title">Start date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="date" class="form-control" id="title" placeholder="startDate">
                            </div>
                            <div class="col-xs-6">
                                <input id="basicExample" type="text" class="time form-control timepicker" size="6"/>
                            </div>
                        </div>
                        <br>

                        <div class="form-group">
                            <label for="endDate">End date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="date" class="form-control" id="endDate" placeholder="endDate">
                            </div>
                            <div class="col-xs-6">
                                <input id="ender" type="text" class="time form-control timepicker" size="6"/>
                            </div>
                        </div>


                        <div class="col-xs-12">
                            <div class="row">
                                <form role="form">
                                    <div class="row col-xs-4">
                                        <br>
                                        <div class="radio-button">
                                            <label><input type="radio" name="optradio" id="no-recurrent"
                                                          class="my-radio" checked>Single event</label>
                                        </div>
                                        <div class="radio-button">
                                            <label><input type="radio" name="optradio" id="weekly" class="my-radio">Weekly</label>
                                        </div>
                                        <div class="radio-button" hidden>
                                            <label><input type="radio" name="optradio" id="monthly" class="my-radio">Monthly</label>
                                        </div>
                                    </div>
                                    <div class="row col-xs-9" id="days-for-recurrent-form" hidden>
                                        <table class="table" id="days-for-recurrent">
                                            <br>
                                            <thead>Check required days</thead>
                                            <tbody>
                                            <tr>
                                                <td><label><input type="checkbox" id="Monday" value="Mon" class="day">Monday</label><br>
                                                </td>
                                                <td><label><input type="checkbox" id="Tuesday" value="Tue" class="day">Tuesday</label><br>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label><input type="checkbox" id="Wednesday" value="Wed"
                                                                  class="day">Wednesday</label><br>
                                                </td>
                                                <td><label><input type="checkbox" id="Thursday" value="Thu" class="day">Thursday</label><br>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label><input type="checkbox" id="Friday" value="Fri" class="day">Friday</label><br>
                                                </td>
                                                <td><label><input type="checkbox" id="Saturday" value="Sat" class="day">Saturday</label><br>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </form>
                                <br><br><br><br><br><br><br><br><br><br>
                                <div class="clearfix"></div>
                                <div class="col-sm-5">
                                    <label for="description">Description</label>
                                    <input type="text" size="15" class="form-control" id="description"
                                           placeholder="description">
                                </div>
                                <br>
                                <div class="clearfix"></div>
                                <div class="deleting-event" id="deleting-recurrent-event" hidden>Click here to delete
                                    this event
                                </div>
                                <div class="col-xs-3">
                                    <button type="button" class="btn btn-success" id="update-recurrent" hidden="true">
                                        Update
                                    </button>
                                    <button type="button" class="btn btn-success live" id="creating">Create</button>
                                </div>
                                <div align="right" class="col-xs-9">
                                    <button type="button" class="btn btn-danger pull-right" id="cancel">Cancel</button>
                                </div>

                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="updating" class="dialog" hidden>
                    <form id="updatingForm">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <label for="titleUpdate">Event title</label>
                            <input type="text" class="form-control" id="titleUpdate" placeholder="title">
                        </div>

                        <div class="form-group">
                            <label for="startDayUpdate">Start date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="startDayUpdate" placeholder="startDate"
                                       readonly>
                            </div>
                            <div class="col-xs-5">
                                <input id="startTimeUpdate" type="text" class="time form-control timepicker" size="6"/>
                            </div>
                        </div>
                        <br>

                        <div class="form-group">
                            <label for="endDateUpdate">End date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="endDateUpdate" placeholder="endDate"
                                       readonly>
                            </div>
                            <div class="col-xs-5">
                                <input id="endTimeUpdate" type="text" class="time form-control timepicker" size="6"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="descriptionUpdate">Description</label>
                            <textarea type="text" class="form-control" id="descriptionUpdate"
                                      placeholder="description"></textarea>
                        </div>
                        <div class="deleting-event" id="deleting-single-event">Click here to delete this event</div>
                        <button type="button" class="btn btn-success" id="updatingButton">Update</button>
                        <button type="button" class="btn btn-danger" id="cancel-update">Cancel</button>

                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="choose-updating-type" class="dialog" hidden>
                    <form id="choose-updating-form">

                        <div class="radio-button">
                            <label><input type="radio" id="single-update" name="radio" checked>Just this one</label>
                        </div>
                        <div class="radio-button">
                            <label><input type="radio" id="recurrent-update" name="radio">The entire series</label>
                        </div>

                        <button type="button" class="btn btn-success" id="confirm-choose">Ok</button>
                        <button type="button" class="btn btn-danger" id="cancel-choose">Cancel</button>

                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="container">
        <button type="button" class="btn btn-success" id="create-new-event">New event</button>
        <div id='calendar'></div>
    </div>

</sec:authorize>
<sec:authorize access="hasRole('ADMINISTRATOR')">

</sec:authorize>

</body>
