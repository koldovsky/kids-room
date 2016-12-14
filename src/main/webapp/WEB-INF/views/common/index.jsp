<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link href='resources/css/fullcalendar.css' rel='stylesheet'/>
<link href='resources/css/fullcalendar.print.css' rel='stylesheet' media='print'/>

<link href='resources/css/formForCalendar.css' rel='stylesheet'/>
<link rel="stylesheet" type="text/css" href="resources/css/jquery.timepicker.css"/>
<link href='resources/css/flow-form.css' rel='stylesheet'/>
<link href='resources/css/manager-no-rooms.css' rel='stylesheet'/>

<link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/dataTables.bootstrap4.min.css" />




<sec:authorize access="hasRole('USER')">
    <div id="mobile" class="container">
            <%--bookingUpdatingDialog--%>
        <div class="row">
            <div class="vertical-center-row">
                <div align="center">
                    <div id="bookingUpdatingDialog" class="dialog" hidden>
                        <form id="bookingUpdatingForm">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group">
                                <label for="bookingUpdatingStartDate">
                                    <spring:message code= "booking.startDate"/>
                                </label>
                                <br>
                                <div class="col-xs-6  ">
                                    <input type="text" class="text-center form-control" id="bookingUpdatingStartDate"
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
                                <label for="bookingUpdatingEndDate">
                                    <spring:message code= "booking.endDate"/>
                                </label>
                                <br>
                                <div class="col-xs-6  ">
                                    <input type="text" class="text-center form-control" id="bookingUpdatingEndDate"
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
                            <br>
                            <div id="data-validation-information-string-container" class="clearfix">
                                <p class="col-xs-12 data-validation-information-string" style="color:red" id="data-validation-information-string"></p>
                            </div>
                            <br>
                            <button type="button" class="btn btn-success" id="updatingBooking">
                                <spring:message code= "booking.update"/>
                            </button>
                            <button type="button" class="btn btn-danger pull-right" id="deletingBookingCancel">
                                <spring:message code= "cancel"/>
                            </button>

                            <div class = "col-xs-12">

                                <footer class="deleteBookingButtonLink">
                                    <div id="deleting-single-booking" style="text-decoration: underline; text-align: center;">
                                        <spring:message code="booking.deleteBooking"/>
                                    </div>
                                </footer>
                            </div>

                                <%--<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#cancelModal">Delete this booking</button>--%>

                        </form>
                    </div>
                </div>
            </div>
        </div>

            <%--make-recurrent-booking--%>
        <div class="row">
            <div class="vertical-center-row">
                <div align="center">
                    <div id="make-recurrent-booking" class="dialog" hidden title=<spring:message code= "booking.newBooking"/>>
                        <form id="recurrent-booking-form">
                            <!--  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> -->


                            <div class="form-group">
                                <label for="recurrent-booking-start-date">
                                    <spring:message code= "booking.startDate"/>
                                </label>
                                <br>
                                <div class="col-xs-6 choose-booking">
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
                                <label for="recurrent-booking-end-date">
                                    <spring:message code= "booking.endDate"/>
                                </label>
                                <br>
                                <div class="col-xs-6 choose-booking">
                                    <input type="date" class="text-center form-control " id="recurrent-booking-end-date"
                                           placeholder="endDate" disabled="true">
                                </div>
                                <div class="col-xs-6">
                                    <input id="recurrent-booking-end-time" type="text" class="text-center time form-control timepicker"
                                           size="6"/>
                                </div>
                            </div>


                            <div class="col-xs-12">
                                <div class="row">
                                    <form role="form">
                                        <div class="row col-xs-5 choose-booking">
                                            <br>
                                            <div class="radio-button">
                                                <label><input type="radio" name="optradio-bookingform"
                                                              id="no-recurrent-booking"
                                                              class="booking-radio" checked>
                                                    <spring:message code= "booking.singleBooking"/>
                                                </label>
                                            </div>
                                            <div class="radio-button">
                                                <label><input type="radio" name="optradio-bookingform" id="weekly-booking"
                                                              class="booking-radio">
                                                    <spring:message code= "booking.weeklyBooking"/>
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-xs-7 pull-right" id="days-for-recurrent-booking-form" hidden>
                                            <table class="table" id="days-for-recurrent-booking">
                                                <br>
                                                <thead><spring:message code="event.checkRequiredDays"/></thead>
                                                <tbody>
                                                <tr>
                                                    <td><label><input type="checkbox" id="Monday-booking" value="Mon"
                                                                      class="day"> <spring:message code= "monday"/></label><br>
                                                    </td>
                                                    <td><label><input type="checkbox" id="Tuesday-booking" value="Tue"
                                                                      class="day"> <spring:message code= "tuestay"/></label><br>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label><input type="checkbox" id="Wednesday-booking" value="Wed"
                                                                      class="day"> <spring:message code= "wednesday"/></label><br>
                                                    </td>
                                                    <td><label><input type="checkbox" id="Thursday-booking" value="Thu"
                                                                      class="day"> <spring:message code= "thursday"/></label><br>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label><input type="checkbox" id="Friday-booking" value="Fri"
                                                                      class="day"> <spring:message code= "friday"/></label><br>
                                                    </td>
                                                    <td><label><input type="checkbox" id="Saturday-booking" value="Sat"
                                                                      class="day"> <spring:message code= "saturday"/></label><br>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </form>

                                    <div class="clearfix"></div>
                                    <div id="child-selector">
                                        <br><spring:message code= "kid.select"/><br>
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

                                    </div>

                                    <div id="comment-for-one-child-updating" hidden>
                                        <textarea class="col-xs-12" id="comment-for-update-recurrency"></textarea>
                                    </div>


                                    <br>
                                    <div id="data-validation-information-string-container" class="clearfix">
                                        <p class="col-xs-12 data-validation-information-string" style="color:red" id="data-validation-information-string"></p>
                                    </div>

                                    <div class="clearfix">
                                            <%--<div class="col-xs-4 row">--%>
                                        <button type="button" class="btn btn-success" id="update-recurrent-booking" hidden="true">
                                            <spring:message code= "booking.update"/>
                                        </button>
                                        <button type="button" class="btn btn-success" id="book">
                                            <spring:message code= "booking.book"/>
                                        </button>
                                            <%--                                    </div>
                                                                                <div class="col-xs-4 row ">--%>
                                        <button type="button" class="btn btn-danger pull-right" id="cancel-changes">
                                            <spring:message code= "cancel"/>
                                        </button>
                                            <%--</div>--%>
                                    </div>
                                    <br>
                                    <div class = "col-xs-12">

                                        <footer class="delete-recurrent-booking">
                                            <div id="delete-recurrent-booking" style="text-decoration: underline; text-align: center;" hidden="true">
                                                <spring:message code="booking.deleteBooking"/>
                                            </div>
                                        </footer>
                                    </div>

                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div id="duplicate-booking-dialog" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        <div align="center">
                            <br>
                            <div class="checkmark">
                                <svg version="1.1" id="Layer_1" x="0px" y="0px"
                                     viewBox="0 0 161.2 161.2" enable-background="new 0 0 161.2 161.2"
                                     xml:space="preserve">
                                            <circle class="path" fill="none" stroke="#FFFFFF" stroke-width="4"
                                                    stroke-miterlimit="10" cx="80.6" cy="80.6" r="62.1"/>
                                    <polyline class="path" fill="none" stroke="#FFFFFF" stroke-width="6"
                                              stroke-linecap="round"
                                              stroke-miterlimit="10" points="113,52.8 74.1,108.4 48.2,86.4 "/>
                                    </svg>
                            </div>
                            <h2><spring:message code= "booking.created"/></h2><br>
                            <h4><spring:message code= "booking.duplicate"/></h4>
                            <button type="button" class="btn btn-success pull-right" id="createDuplicateBooking">
                                <spring:message code= "yes"/>
                            </button>
                            <button type="button" class="btn btn-danger pull-right" id="omitCreateDuplicateBooking">
                                <spring:message code= "no"/>
                            </button>
                            <br><br>
                        </div>
                    </div>
                </div>
            </div>
        </div>

            <%--recurrent-change--%>
        <div class="row">
            <div class="vertical-center-row">
                <div align="center">
                    <div id="recurrent-change" title = "<spring:message code= "booking.edit"/>" class="dialog" hidden>
                        <form id="choose-updating-booking-form">
                            <div class="radio-button">
                                <label><input type="radio" id="single-update-booking" name="radio-check" checked>
                                    <spring:message code= "recurrent.justThisOneBooking"/>
                                </label>
                            </div>
                            <div class="radio-button">
                                <label><input type="radio" id="recurrent-update-booking" name="radio-check">
                                    <spring:message code= "recurrent.allSeries"/>
                                </label>
                            </div>

                            <button type="button" class="btn btn-success" id="confirm-choose-booking">
                                <spring:message code= "ok"/>
                            </button>
                            <button type="button" class="btn btn-danger pull-right" id="close-choose">
                                <spring:message code= "cancel"/>
                            </button>

                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="eventInfo">
                <h3 id="eventTitle">Title</h3>
                <span class="time" id="startTime"></span>
                <span class="time" id="endTime"></span><br/>
                <div class="eventDescription">
                    <span id="eventDescription"></span>
                </div>
                <div class="text-area">
                    <textarea id="text-area" readonly="readonly"> </textarea>
                </div>
            </div>

        </div>

            <%--create-new-booking--%>
        <div class="container col-xs-12">
            <button type="button" class="btn btn-success btn-responsive pull-right" data-toggle="modal"
                    data-target=".bs-modal-lg-colourInfo" >
                <span class="glyphicon glyphicon-info-sign"> </span>
            </button>
            <button type="button" class="btn btn-success btn-responsive" id="create-new-booking">
                <spring:message code= "booking.makeBooking"/>
            </button>
            <button type="button" class="btn btn-success btn-responsive pull-right" data-toggle="modal"
                    data-target=".bs-modal-lg-contact">
                <spring:message code= "booking.contact"/>
            </button>

            <div class="modal fade bs-modal-lg-colourInfo" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-content">
                        <div class="modal-body">
                            <div align="center">
                                <br>
                                <div class="col-xs-4">
                                    <h1  > <span style="vertical-align:middle ;color: #4CAF50;" class="glyphicon glyphicon-stop" ></span></h1> <spring:message code= "yourBooking"/>
                                </div> <div class="col-xs-4">
                                <h1  > <span style="vertical-align:middle ;color: #EEEEEE;" class="glyphicon glyphicon-stop" ></span></h1> <spring:message code= "room"/>
                            </div> <div class="col-xs-4">
                                <h1  > <span style="vertical-align:middle ;color: #ff0000;" class="glyphicon glyphicon-stop" ></span></h1> <spring:message code= "bookedRoom"/>
                            </div>
                                <div class="col-xs-12">
                                    <h3  >
                                        <span style="vertical-align:middle ;color: #d3af37;" class="glyphicon glyphicon-stop" ></span>
                                        <span style="vertical-align:middle ;color: #84fff7;" class="glyphicon glyphicon-stop" ></span>
                                        <span style="vertical-align:middle ;color: #4CAF50;" class="glyphicon glyphicon-stop" ></span>
                                        <span style="vertical-align:middle ;color: #f98e2e;" class="glyphicon glyphicon-stop" ></span>
                                        <span style="vertical-align:middle ;color: #636363;" class="glyphicon glyphicon-stop" ></span>
                                    </h3>
                                    <h3  > <span style="vertical-align:middle ;color: #1ba1e2;" class="glyphicon glyphicon-stop" ></span>
                                        <span style="vertical-align:middle ;color: #044d92;" class="glyphicon glyphicon-stop" ></span>
                                        <span id="loadEaster" style="vertical-align:middle ;color: #9b3aa1;" class="glyphicon glyphicon-stop" ></span>
                                        <span style="vertical-align:middle ;color: #ffcd5c;" class="glyphicon glyphicon-stop" ></span>
                                        <span style="vertical-align:middle ;color: #eb6f63;" class="glyphicon glyphicon-stop" ></span>  </h3>
                                    <spring:message code= "events"/>
                                    <br>
                                    <body class="kidsInfo" onload="init();" >
                                    <canvas id="blob" width="600" height="400" hidden> </canvas>
                                    <br>
                                    </body>
                                    <br>
                                </div>
                                <span id="softServeInc">SoftServe Inc</span>
                            </div>
                        </div>
                        </div>
                    </div>
                </div>
            </div>

        <div class="modal fade bs-modal-lg-contact" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">

                    <div class="modal-body">

                        <div align="center">

                            <br>
                            <h4 id="showRoomManagers"></h4>

                            <H4 id="roomPhone"></H4>

                            <br>

                            <span>SoftServe Inc</span>



                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id='user-calendar'></div>
    </div>

    <%--confirmation-dialog--%>
    <div class="modal fade">
        <div class="modal-dialog">
            <div aclass="modal-body text-center">
                <div id="confirmation-dialog-div" class="ui-dialog"  title=<spring:message code= "booking.confirmTitle" /> hidden>
                    <form id="confirm-your-choice">
                        <div class= "confirmDelete">
                            <p><span style="text-align:center;" >
                                <spring:message code= "booking.confirmCancelQuestion1"/> </span> </p>
                            <p><span style="text-align:center;" >
                                <spring:message code= "booking.confirmCancelQuestion2"/></span> </p>
                        </div>
                        <div class="col-xs-12" >
                            <div class="col-xs-6" style = "text-align: center">
                                <button type="button"  class="btn btn-success" id="confirmYes">
                                    <spring:message code= "booking.confirmYes"/>
                                </button>
                            </div>
                            <div class = col-xs-6" style = "text-align: center">
                                <button type="button" class="btn btn-danger" id="confirmNo">
                                    <spring:message code= "booking.confirmNo"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="loading" hidden>Loading&#8230;</div>
    </div>
</sec:authorize>
<sec:authorize access="hasRole('MANAGER')">
    <c:forEach items="${managersRoom}" var="r">
        ${r.id}
    </c:forEach>
    <c:if test="${rooms.isEmpty()}">
        <div id="manager-no-rooms">
            <h1>
          <span class="label label-warning" id="radius">
          <spring:message code="manager.oops"/></span>
            </h1>
            <h3 class="center"><spring:message code="manager.noRooms" /></h3>
        </div>
        <div class="desert">
            <div class="tumbleweed"></div>
        </div>
    </c:if>
    <c:if test="${!rooms.isEmpty()}">
        <div class="row">
            <div class="vertical-center-row">
                <div align="center">
                    <div id="dialog-recurrently" class="modal-dialog-recurrently dialog" hidden>
                        <form id="form-dialog-recurrently">
                            <div class="form-group">
                                <label for="recurrent-event-title"><spring:message code="eventTitle"/></label>
                                <input type="text" class="form-control" id="recurrent-event-title" placeholder="title">
                            </div>
                            <div class="form-group">
                                <label for="recurrent-event-start-date"><spring:message code="booking.startDate"/></label>
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
                                <label for="recurrent-event-end-date"><spring:message code="booking.endDate"/></label>
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
                            <label for="recurrent-event-description"><spring:message code="recurrent.description" /></label>
                            <textarea class="form-control" id="recurrent-event-description"
                                      placeholder="description" cols="30"></textarea>
                            <div class="col-xs-6">
                                <button type="button" class="btn btn-success" id="recurrent-event-create"><spring:message code="booking.create"/></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="vertical-center-row">
                <div align="center">
                    <div id="dialog" class="dialog" hidden title="<spring:message code= "event.new"/>">
                        <form id="form">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="col-xs-12 form-group">
                                <label for="event-title"><spring:message code= "event.title"/></label>
                                <spring:message code="event.titlePlaceHolder" var="titlePlaceHolder"/>
                                <input type="text" class="form-control" id="event-title" placeholder="${titlePlaceHolder}">
                            </div>
                            <div class="form-group">
                                <label for="color-select"><spring:message code="event.chooseColor" /></label>
                                <select id="color-select">
                                    <option value="#eb6f63" style="background:red">
                                        <spring:message code= "color.red"/>
                                    </option>
                                    <option value="#ffcd5c" style="background:yellow">
                                        <spring:message code= "color.yellow"/>
                                    </option>
                                    <option value="#9b3aa1" style="background:purple">
                                        <spring:message code= "color.purple"/>
                                    </option>
                                    <option value="#044d92" style="background:blue">
                                        <spring:message code= "color.blue"/>
                                    </option>
                                    <option value="#1ba1e2" style="background:#6AA4C1" selected="selected">
                                        <spring:message code= "color.lightBlue"/>
                                    </option>
                                    <option value="#636363" style="background:grey">
                                        <spring:message code= "color.grey"/>
                                    </option>
                                    <option value="#51d466" style="background:green">
                                        <spring:message code= "color.green"/>
                                    </option>
                                    <option value="#f98e2e" style="background:orange">
                                        <spring:message code= "color.orange"/>
                                    </option>
                                    <option value="#84fff7" style="background:aqua">
                                        <spring:message code= "color.aqua"/>
                                    </option>
                                    <option value="#d3af37" style="background:gold">
                                        <spring:message code= "color.gold"/><br></option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="start-date-picker"><spring:message code= "event.startDate"/></label>
                                <br>
                                <div class="col-xs-6">
                                    <input id="start-date-picker" type="date" class="text-center form-control"  placeholder="startDate">
                                </div>
                                <div class="col-xs-6">
                                    <input id="start-time-picker" type="text" class=" text-center time form-control timepicker" size="6"/>
                                </div>
                            </div>
                            <br>

                            <div class="form-group">
                                <label for="end-date-picker"><spring:message code= "event.endDate"/></label>
                                <br>
                                <div class="col-xs-6">
                                    <input id="end-date-picker" type="date" class="text-center form-control" placeholder="endDate"
                                           disabled="true">
                                </div>
                                <div class="col-xs-6">
                                    <input id="end-time-picker" type="text" class="text-center time form-control timepicker" size="6"/>
                                </div>
                            </div>


                            <div class="col-xs-12">
                                <div class="row">
                                    <form role="form">
                                        <div class="row col-xs-4">
                                            <br>
                                            <div class="radio-button">
                                                <label><input type="radio" name="optradio" id="single-event-radio-button"
                                                              class="my-radio" checked>
                                                    <spring:message code="event.singleEvent"/></label>
                                            </div>
                                            <div class="radio-button">
                                                <label><input type="radio" name="optradio" id="weekly-radio-button" class="my-radio">
                                                    <spring:message code="event.weeklyEvent"/></label>
                                            </div>
                                            <div class="radio-button">
                                                <label><input type="radio" name="optradio" id="monthly-radio-button" class="my-radio">
                                                    <spring:message code="event.monthlyEvent"/></label>
                                            </div>
                                        </div>
                                        <div class="row col-xs-7" id="days-for-monthly-form" hidden>
                                            <br><spring:message code="event.checkRequiredDays"/>
                                            <table class = "table center" id="monthly-days">
                                            </table>
                                        </div>
                                        <div class="row col-xs-9" id="days-for-recurrent-form" hidden>
                                            <table class="table" id="days-for-recurrent">
                                                <br>
                                                <thead > <spring:message code="event.checkRequiredDays"/></thead>
                                                <tbody>
                                                <tr>
                                                    <td><label><input type="checkbox" id="Monday" value="Mon" class="day">
                                                        <spring:message code="monday"/></label><br>
                                                    </td>
                                                    <td><label><input type="checkbox" id="Tuesday" value="Tue" class="day">
                                                        <spring:message code="tuestay"/></label><br>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label><input type="checkbox" id="Wednesday" value="Wed"
                                                                      class="day"> <spring:message code="wednesday"/></label><br>
                                                    </td>
                                                    <td><label><input type="checkbox" id="Thursday" value="Thu" class="day">
                                                        <spring:message code="thursday"/></label><br>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label><input type="checkbox" id="Friday" value="Fri" class="day">
                                                        <spring:message code="friday"/></label><br>
                                                    </td>
                                                    <td><label><input type="checkbox" id="Saturday" value="Sat" class="day">
                                                        <spring:message code="saturday"/></label><br>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </form>
                                    <br><br><br><br><br><br><br><br><br><br>
                                    <div class="clearfix"></div>
                                    <div class="col-sm-12">
                                        <spring:message code="event.descriptionPlaceHolder" var="descriptionPlaceHolder"/>
                                        <label for="description"> <spring:message code="event.labelForDescription"></spring:message> </label>
                                        <input type="text" size="15" class="form-control" id="description"
                                               placeholder="${descriptionPlaceHolder}">
                                    </div>
                                    <br>
                                    <div id="data-validation-information-string-container" class="clearfix">
                                        <p class="col-xs-12 data-validation-information-string" style="color:red" id="data-validation-information-string"></p>
                                    </div>
                                    <br>
                                    <div class="clearfix"></div>
                                    <div class="col-xs-3">
                                        <button type="button" class="btn btn-success" id="update-recurrent-button" hidden="true">
                                            <spring:message code="booking.update"/>
                                        </button>
                                        <button type="button" class="btn btn-success live" id="create-button"><spring:message code="booking.create"/></button>
                                    </div>
                                    <div align="right" class="col-xs-9">
                                        <button type="button" class="btn btn-danger pull-right" id="cancel"><spring:message code="cancel"/></button>
                                    </div>
                                    <div class = "col-xs-12">
                                        <footer class="deleteBookingButtonLink">
                                            <div id="deleting-recurrent-event" style="text-decoration: underline; text-align: center;">
                                                <spring:message code="event.deleteRecurrentEvent"/>
                                            </div>
                                        </footer>
                                    </div>

                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="vertical-center-row">
                <div align="center">
                    <div id="updating" class="dialog" hidden title = "<spring:message code="event.changeEvent"></spring:message>">
                        <form id="updatingForm">

                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group">
                                <label for="titleUpdate"> <spring:message code="event.labelForTitle"></spring:message> </label>
                                <spring:message code="event.titlePlaceHolder" var="titlePlaceHolder"/>
                                <input type="text" class="form-control" id="titleUpdate" placeholder="${titlePlaceHolder}">
                            </div>

                            <div class="form-group">
                                <label for="color-select"> <spring:message code="event.chooseColor"></spring:message> </label>
                                <select id="color-select-single-event">
                                    <option value="#eb6f63" style="background:red">
                                        <spring:message code= "color.red"/>
                                    </option>
                                    <option value="#ffcd5c" style="background:yellow">
                                        <spring:message code= "color.yellow"/>
                                    </option>
                                    <option value="#9b3aa1" style="background:purple">
                                        <spring:message code= "color.purple"/>
                                    </option>
                                    <option value="#044d92" style="background:blue">
                                        <spring:message code= "color.blue"/>
                                    </option>
                                    <option value="#1ba1e2" style="background:#6AA4C1" selected="selected">
                                        <spring:message code= "color.lightBlue"/>
                                    </option>
                                    <option value="#636363" style="background:grey">
                                        <spring:message code= "color.grey"/>
                                    </option>
                                    <option value="#51d466" style="background:green">
                                        <spring:message code= "color.green"/>
                                    </option>
                                    <option value="#f98e2e" style="background:orange">
                                        <spring:message code= "color.orange"/>
                                    </option>
                                    <option value="#84fff7" style="background:aqua">
                                        <spring:message code= "color.aqua"/>
                                    </option>
                                    <option value="#d3af37" style="background:gold">
                                        <spring:message code= "color.gold"/><br></option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="startDayUpdate"> <spring:message code="event.startDate"></spring:message> </label>
                                <br>
                                <div class="col-xs-6">
                                    <input type="text" class="form-control" id="startDayUpdate" placeholder="startDate"
                                           readonly>
                                </div>
                                <div class="col-xs-6">
                                    <input id="startTimeUpdate" type="text" class="text-center time form-control timepicker" size="6"/>
                                </div>
                            </div>
                            <br>

                            <div class="form-group">
                                <label for="endDateUpdate"> <spring:message code="event.endDate"></spring:message> </label>
                                <br>
                                <div class="col-xs-6">
                                    <input type="text" class="form-control" id="endDateUpdate" placeholder="endDate"
                                           readonly>
                                </div>
                                <div class="col-xs-6">
                                    <input id="endTimeUpdate" type="text" class="text-center time form-control timepicker" size="6"/>
                                </div>
                            </div>
                            <br>
                            <div class="form-group">
                                <label for="descriptionUpdate">
                                    <spring:message code="event.description"></spring:message>
                                </label>
                                <spring:message code="event.descriptionPlaceHolder" var="descriptionPlaceHolder"/>
                                <textarea type="text" class="form-control" id="descriptionUpdate"
                                          placeholder=${descriptionPlaceHolder}></textarea>
                            </div>
                            <br>
                            <div id="data-validation-information-string-container" class="clearfix">
                                <p class="col-xs-12 data-validation-information-string" style="color:red" id="data-validation-information-string"></p>
                            </div>
                            <br>
                            <button type="button" class="btn btn-success" id="updatingButton">
                                <spring:message code="event.update"></spring:message>
                            </button>
                            <button type="button" class="pull-right btn btn-danger" id="cancel-update">
                                <spring:message code="cancel"></spring:message>
                            </button>

                            <div class = "col-xs-12">
                                <footer class="deleteEventButtonLink">
                                    <div id="deleting-single-event" style="text-decoration: underline; text-align: center;">
                                        <spring:message code="event.deleteEvent"/>
                                    </div>
                                </footer>
                            </div>


                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="vertical-center-row">
                <div align="center">
                    <div id="choose-updating-type" class="dialog" hidden title=<spring:message code= "recurrent.event.title"/>>
                        <form id="choose-updating-form">

                            <div class="lableBoard">
                                <label><small><i><ins><spring:message code="recurrent.editEventMessage"/></ins></i></small></label>
                            </div>
                            <div class="radio-button">
                                <label><input type="radio" id="single-update" name="radio" checked> <spring:message code="recurrent.justThisOneEvent"/></label>
                            </div>
                            <div class="radio-button">
                                <label><input type="radio" id="recurrent-update" name="radio"> <spring:message code="recurrent.allSeries"/></label>
                            </div>
                            <button type="button" class="btn btn-success btn-edit-event" id="confirm-choose"> <spring:message code="ok"/></button>
                            <button type="button" class="btn btn-danger btn-edit-event" id="cancel-choose"> <spring:message code="cancel"/></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <button type="button" class="btn btn-success" id="create-new-event"><spring:message code="event.new"/></button>
            <div id='calendar'></div>
        </div>
    </c:if>

    <%--confirmation-dialog--%>
    <div class="row">
        <div class="modal-dialog modal-lg vertical-center-row ">
            <div align="center">
                <div id="confirmation-dialog-event-div" class="ui-dialog"  title=<spring:message code= "event.confirmTitle" /> hidden>
                    <form id="confirm-your-choice-event">
                        <div class= "confirmDelete">
                            <p><span style="text-align:center;" >
                                <spring:message code= "event.confirmCancelQuestion1"/> </span> </p>
                            <p><span style="text-align:center;" >
                                <spring:message code= "event.confirmCancelQuestion2"/></span> </p>
                        </div>
                        <div class="col-xs-12" >
                            <div class="col-xs-6" style = "text-align: center">
                                <button type="button"  class="btn btn-success" id="confirmYesEvent">
                                    <spring:message code= "event.confirmYes"/>
                                </button>
                            </div>
                            <div class = col-xs-6" style = "text-align: center">
                                <button type="button" class="btn btn-danger" id="confirmNoEvent">
                                    <spring:message code= "event.confirmNo"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <%--cancel-event-dialog--%>
    <div class="row">
        <div class="vertical-center-row">
            <div align="center">
                <div id="cancel-event-dialog" class="dialog" hidden title="<spring:message code= "event.cancel"/>">
                    <form id="form-event-cancel">
                        <div>
                            <p style="color:red;"><spring:message code= "cancel.warning" arguments='<span id="event-cancel-title"></span>'/></p>
                        </div>
                        <br>
                        <div class="form-group">
                            <label align-items="center"  for="start-date-cancel-picker"><spring:message code= "event.startDate"/></label>

                            <div class=>
                                <input id="start-date-cancel-picker" type="date" class=" form-control"  placeholder="startDate">
                            </div>
                        </div>

                        <div class="form-group">
                            <label align="center" for="end-date-cancel-picker"><spring:message code= "event.endDate"/></label>
                            <br>
                            <div class=>
                                <input id="end-date-cancel-picker" type="date" class="form-control" placeholder="endDate">
                            </div>
                        </div>


                        <div class="col-xs-12">
                            <div class="row">
                                <div class="clearfix"></div>
                                <br>
                                <div id="validation-information-container" class="clearfix">
                                    <p class="col-xs-12 data-validation-information-string" style="color:red" id="validation-information"></p>
                                </div>
                                <br>
                                <div class="clearfix"></div>
                                <div class="col-xs-3">
                                    <button type="button" class="btn btn-success live" id="yes-cancel-event-button"><spring:message code="yes"/></button>
                                </div>
                                <div align="right" class="col-xs-9">
                                    <button type="button" class="btn btn-danger pull-right" id="no-cancel-event-button"><spring:message code="no"/></button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</sec:authorize>
<sec:authorize access="hasRole('ADMINISTRATOR')">

</sec:authorize>


<%--error-dialog--%>
<div id="error-dialog" type="hidden"></div>

<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap4.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/moment.min.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/jquery.timepicker.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/fullcalendar.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/seriousColorLegendUpdate.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/manager-create-events-validator.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/validation/user-create-booking-validator.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/single-booking.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/renderCalendar.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/header-manager.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/header-user.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/userCalendar.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/recurrent-cancel-validator.js'></script>
