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

<script src='resources/js/userCalendar.js'></script>

<script src='resources/js/renderCalendar.js'></script>

<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">


<link href='resources/css/formForCalendar.css' rel='stylesheet'/>


<script type="text/javascript" src="resources/js/jquery.timepicker.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/jquery.timepicker.css"/>


<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>

<body>

<sec:authorize access="hasRole('USER')">

    <spring:message code="user.selectRoom"/>
    <c:forEach items="${managersRoom}" var="r">

        ${r.id}

    </c:forEach>


    <select id="selectBoxUser" onchange="selectRoomForUser(value, '${userId}');">

        <option value=" "></option>

        <c:forEach items="${rooms}" var="r">

            <option value="${r.id}">${r.city}: ${r.address}</option>

        </c:forEach>

    </select>


    <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="bookingDialog" hidden>
                    <form id="bookingForm">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">

                            <label for="bookingStartDate">Start date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="bookingStartDate" placeholder="startDate"
                                       readonly>
                            </div>
                            <div class="col-xs-5">
                                <input id="bookingStartTimepicker" type="text" class="time form-control" size="6"
                                       name="bookingStartTimepicker"/>
                            </div>
                        </div>
                        <br>


                        <div class="form-group">
                            <label for="bookingEndDate">End date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="bookingEndDate" placeholder="endDate"
                                       readonly>
                            </div>
                            <div class="col-xs-5">
                                <input id="bookingEndTimepicker" type="text" class="time form-control" size="6"
                                       name="bookingEndTimepicker"/>
                            </div>
                        </div>

                        <br>Select children for bookings:<br>
                        <table>
                            <c:forEach items="${kids}" var="kids" varStatus="loop">

                                <c:set var="kidsArray" value="${kids}"/>


                                <tr>
                                    <label><input type="checkbox" value=""
                                                  id="checkboxKid${kids.id}">${kids.firstName}</label>
                                    <br>
                                </tr>

                            </c:forEach>

                            <c:forEach items="${kids}" var="kids" varStatus="loop">

                                <tr>
                                    <label for="child-comment-${kids.id}" id="child-comment-${kids.id}-1" hidden>Comment
                                        for ${kids.firstName}:</label>

                                    <textarea type="text" id="child-comment-${kids.id}" hidden></textarea>

                                    <input type="text" id="comment-${loop.index}" value="${kids.id}" hidden>
                                    <br>
                                </tr>


                            </c:forEach>
                            <input id="number-of-kids" hidden value="${fn:length(kids)}">
                        </table>

                        <div class="col-xs-6">
                            <button type="button" class="btn btn-success" id="booking">Book</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="bookingUpdatingDialog" hidden>
                    <form id="bookingUpdatingForm">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <label for="bookingUpdatingStartDate">Start date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="bookingUpdatingStartDate"
                                       placeholder="startDate"
                                       readonly>
                            </div>
                            <div class="col-xs-5">
                                <input id="bookingUpdatingStartTimepicker" type="text" class="time form-control"
                                       size="6"/>
                            </div>
                        </div>
                        <br>


                        <div class="form-group">
                            <label for="bookingUpdatingEndDate">End date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="bookingUpdatingEndDate"
                                       placeholder="endDate"
                                       readonly>
                            </div>
                            <div class="col-xs-5">
                                <input id="bookingUpdatingEndTimepicker" type="text" class="time form-control"
                                       size="6"/>
                            </div>
                        </div>

                        <textarea type="text" id="child-comment-update"></textarea>

                        <div class="col-xs-6">
                            <button type="button" class="btn btn-success" id="updatingBooking">Save</button>
                        </div>

                        <div class="col-xs-6">
                            <button type="button" class="btn btn-danger" id="deletingBooking">Delete</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>

    <div id='user-calendar'></div>


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
                                <input type="date" class="form-control" id="recurrent-event-start-date" placeholder="startDate">
                            </div>
                            <div class="col-xs-5">
                                <input id="recurrent-event-start-time" type="text" class="time form-control timepicker" size="6"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="recurrent-event-end-date">End date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="date" class="form-control" id="recurrent-event-end-date" placeholder="endDate">
                            </div>
                            <div class="col-xs-5">
                                <input id="recurrent-event-end-time" type="text" class="time form-control timepicker" size="6"/>
                            </div>
                        </div>

                        <br><br>
                        <table class="table">
                            <thead>Check required days</thead>
                            <tbody>
                            <tr>
                                <td><label><input type="checkbox" id="Monday" value="">Monday </label><br></td>
                                <td><label><input type="checkbox" id="Tuesday" value="">Tuesday</label><br></td>
                            </tr>
                            <tr>
                                <td><label><input type="checkbox" id="Wednesday" value="">Wednesday </label><br>
                                </td>
                                <td><label><input type="checkbox" id="Thursday" value="">Thursday</label><br></td>
                            </tr>
                            <tr>
                                <td><label><input type="checkbox" id="Friday" value="">Friday </label><br></td>
                                <td><label><input type="checkbox" id="Saturday" value="">Saturday</label><br></td>
                            </tr>
                            </tbody>
                        </table>

                        <div class="form-group">
                            <label for="recurrent-event-description">Description</label>
                            <textarea type="text" class="form-control" id="recurrent-event-description"
                                      placeholder="description"></textarea>
                        </div>
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

                            <div class="text-center">
                                <button type="button" class="btn btn-info" id="recurrent">Recurrent</button>
                            </div>

                            <label for="startDate">Event title</label>
                            <input type="text" class="form-control" id="startDate" placeholder="title">
                        </div>

                        <div class="form-group">
                            <label for="title">Start date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="title" placeholder="startDate" readonly>
                            </div>
                            <div class="col-xs-5">
                                <input id="basicExample" type="text" class="time form-control timepicker" size="6"/>
                            </div>
                        </div>
                        <br>


                        <div class="form-group">
                            <label for="endDate">End date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="endDate" placeholder="endDate" readonly>
                            </div>
                            <div class="col-xs-5">
                                <input id="ender" type="text" class="time form-control timepicker" size="6"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <label for="Description">Description</label>
                            <textarea type="text" class="form-control" id="description"
                                      placeholder="description"></textarea>
                        </div>
                        <div class="col-xs-6">
                            <button type="button" class="btn btn-success" id="creating">Create</button>
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

                        <button type="button" class="btn btn-success" id="updatingButton">Update</button>
                        <button type="button" class="btn btn-danger" id="deleting">Delete</button>

                    </form>
                </div>
            </div>
        </div>
    </div>


    <div id='calendar'></div>


</sec:authorize>
<sec:authorize access="hasRole('ADMINISTRATOR')">

</sec:authorize>

</body>