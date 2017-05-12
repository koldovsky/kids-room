<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href='${pageContext.request.contextPath}/resources/css/lib/jquery-ui.css'>
<link href='${pageContext.request.contextPath}/resources/css/fullcalendar.css' rel='stylesheet'/>
<link href='${pageContext.request.contextPath}/resources/css/fullcalendar.print.css' rel='stylesheet' media='print'/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/lib/animate.min.css">

<link href='${pageContext.request.contextPath}/resources/css/formForCalendar.css' rel='stylesheet'/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery.timepicker.css"/>
<link href='${pageContext.request.contextPath}/resources/css/flow-form.css' rel='stylesheet'/>
<link href='${pageContext.request.contextPath}/resources/css/manager-no-rooms.css' rel='stylesheet'/>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/lib/dataTables.bootstrap4.min.css"/>
<div id="mobile" class="container">
    <%--userEventDescription--%>
    <div class="row">
        <div class="vertical-center-row">
            <div align="center">
                <div id="userDescriptionDialog" class="dialog" hidden>
                    <form id="eventDescriptionForm">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <div class="form-group">
                            <label class="col-xs-6" for="eventDescriptionStartAt">
                                <spring:message code="event.startAt"/>
                                <input type="text" class="text-center form-control" id="eventDescriptionStartAt"
                                       readonly>
                            </label>
                            <label class="col-xs-6" for="eventDescriptionEndAt">
                                <spring:message code="event.endAt"/>
                                <input type="text" class="text-center form-control" id="eventDescriptionEndAt"
                                       readonly>
                            </label>
                            <br>
                        </div>
                        <label>
                            <spring:message code="event.description"/>
                        </label>
                        <textarea class="col-xs-12" type="text" id="user-description"></textarea>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <%--bookingUpdatingDialog--%>
    <div class="row">
        <div class="vertical-center-row">
            <div align="center">
                <div id="bookingUpdatingDialog" class="dialog" hidden>
                    <form id="bookingUpdatingForm">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <label for="bookingUpdatingStartDate">
                                <spring:message code="booking.startDate"/>
                            </label>
                            <br>
                            <div class="col-xs-6  ">
                                <input type="text" class="text-center form-control datepickers"
                                       id="bookingUpdatingStartDate"
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
                                <spring:message code="booking.endDate"/>
                            </label>
                            <br>
                            <div class="col-xs-6  ">
                                <input type="text" class="text-center form-control datepickers"
                                       id="bookingUpdatingEndDate"
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
                            <p class="col-xs-12 data-validation-information-string" style="color:red"
                               id="data-validation-information-string"></p>
                        </div>
                        <br>
                        <button type="button" class="btn btn-success" id="updatingBooking">
                            <spring:message code="booking.update"/>
                        </button>
                        <button type="button" class="btn btn-danger pull-right" id="deletingBookingCancel">
                            <spring:message code="cancel"/>
                        </button>

                        <div class="col-xs-12">

                            <footer class="deleteBookingButtonLink">
                                <div id="deleting-single-booking"
                                     style="text-decoration: underline; text-align: center;">
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
                <div id="make-recurrent-booking" class="dialog" hidden title=<spring:message
                        code="booking.newBooking"/>>
                    <form id="recurrent-booking-form">
                        <!--  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> -->


                        <div class="form-group">
                            <label for="recurrent-booking-start-date">
                                <spring:message code="booking.startDate"/>
                            </label>
                            <br>
                            <div class="col-xs-6 choose-booking">
                                <input type="text" class="text-center form-control datepickers"
                                       id="recurrent-booking-start-date"
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
                                <spring:message code="booking.endDate"/>
                            </label>
                            <br>
                            <div class="col-xs-6 choose-booking">
                                <input type="text" class="text-center form-control datepickers"
                                       id="recurrent-booking-end-date"
                                       placeholder="endDate" disabled="true">
                            </div>
                            <div class="col-xs-6">
                                <input id="recurrent-booking-end-time" type="text"
                                       class="text-center time form-control timepicker"
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
                                                <spring:message code="booking.singleBooking"/>
                                            </label>
                                        </div>
                                        <div class="radio-button">
                                            <label><input type="radio" name="optradio-bookingform"
                                                          id="weekly-booking"
                                                          class="booking-radio">
                                                <spring:message code="booking.weeklyBooking"/>
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
                                                                  class="day"> <spring:message
                                                        code="monday"/></label><br>
                                                </td>
                                                <td><label><input type="checkbox" id="Tuesday-booking" value="Tue"
                                                                  class="day"> <spring:message
                                                        code="tuestay"/></label><br>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label><input type="checkbox" id="Wednesday-booking" value="Wed"
                                                                  class="day"> <spring:message
                                                        code="wednesday"/></label><br>
                                                </td>
                                                <td><label><input type="checkbox" id="Thursday-booking" value="Thu"
                                                                  class="day"> <spring:message
                                                        code="thursday"/></label><br>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label><input type="checkbox" id="Friday-booking" value="Fri"
                                                                  class="day"> <spring:message
                                                        code="friday"/></label><br>
                                                </td>
                                                <td><label><input type="checkbox" id="Saturday-booking" value="Sat"
                                                                  class="day"> <spring:message
                                                        code="saturday"/></label><br>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </form>

                                <div class="clearfix"></div>
                                <div id="child-selector">
                                    <br><spring:message code="kid.select"/><br>
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

                                                <textarea type="text" class="col-xs-12"
                                                          id="child-comment-${kids.id}"
                                                          hidden></textarea>

                                                <input type="text" id="comment-${loop.index}" value="${kids.id}"
                                                       hidden>
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
                                    <p class="col-xs-12 data-validation-information-string" style="color:red"
                                       id="data-validation-information-string"></p>
                                </div>

                                <div class="clearfix">
                                    <%--<div class="col-xs-4 row">--%>
                                    <button type="button" class="btn btn-success" id="update-recurrent-booking"
                                            hidden="true">
                                        <spring:message code="booking.update"/>
                                    </button>
                                    <button type="button" class="btn btn-success" id="book">
                                        <spring:message code="booking.book"/>
                                    </button>
                                    <%--                                    </div>
                                                                        <div class="col-xs-4 row ">--%>
                                    <button type="button" class="btn btn-danger pull-right" id="cancel-changes">
                                        <spring:message code="cancel"/>
                                    </button>
                                    <%--</div>--%>
                                </div>
                                <br>
                                <div class="col-xs-12">

                                    <footer class="delete-recurrent-booking">
                                        <div id="delete-recurrent-booking"
                                             style="text-decoration: underline; text-align: center;" hidden="true">
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

    <div id="duplicate-booking-dialog" class="modal fade" tabindex="-1" role="dialog"
         aria-labelledby="myLargeModalLabel">
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
                        <h2><spring:message code="booking.created"/></h2><br>
                        <h4><spring:message code="booking.duplicate"/></h4>
                        <button type="button" class="btn btn-success pull-right" id="createDuplicateBooking">
                            <spring:message code="yes"/>
                        </button>
                        <button type="button" class="btn btn-danger pull-right" id="omitCreateDuplicateBooking">
                            <spring:message code="no"/>
                        </button>
                        <br><br>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="vertical-center-row">
            <div align="center">
                <div id="personal-discounts-dialog" class="dialog" hidden
                     title="<spring:message code="administrator.personalDiscount"/>">
                    <div align="center">
                        <table id="discount-table">
                            <thead>
                            <tr>
                                <td><spring:message code="administrator.dayDiscount.startDate"/></td>
                                <td><spring:message code="administrator.personalDiscount.value"/></td>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="vertical-center-row">
            <div align="center">
                <div id="abonnement-info" class="dialog" hidden
                     title="<spring:message code="administrator.abonnementsInfo"/>">
                    <div align="center">
                        <table id="abonnement-table">
                            <thead>
                            <tr>
                                <td><spring:message code="administrator.abonnement"/></td>
                                <td><spring:message code="administrator.abonnement.price"/></td>
                                <td><spring:message code="administrator.abonnement.hours"/></td>
                                <td><spring:message code="administrator.abonnement.remainingTime"/></td>
                                <td><spring:message code="administrator.abonnement.assignTime"/></td>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                        <button type="button" class="btn btn-success btn-responsive"
                                id="buy-abonnements">
                            Buy Abonnement
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="vertical-center-row">
            <div align="center">
                <div id="abonnement-buy-dialog" class="dialog" hidden
                     title="Buy Abonnements Modal">
                    <div align="center">
                        <table id="buy-abonnement-table">
                            <thead>
                            <tr>
                                <td><spring:message code="administrator.abonnement"/></td>
                                <td><spring:message code="administrator.abonnement.price"/></td>
                                <td><spring:message code="administrator.abonnement.hours"/></td>
                                <td>Order</td>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                        <button type="button" class="btn btn-success btn-responsive pull-left"
                                id="order-abonnement">
                            Buy
                        </button>
                        <button type="button" class="btn btn-danger btn-responsive pull-right"
                                id="cancel-abonnement">
                            Cancel
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--recurrent-change--%>
    <div class="row">
        <div class="vertical-center-row">
            <div align="center">
                <div id="recurrent-change" title="<spring:message code="booking.edit"/>" class="dialog" hidden>
                    <form id="choose-updating-booking-form">
                        <div class="lableBoard">
                            <label class="small">
                                <spring:message code="recurrent.editMessage"/>
                            </label>
                        </div>
                        <div class="radio-button">
                            <label><input type="radio" id="single-update-booking" name="radio-check" checked>
                                <spring:message code="recurrent.justThisOneBooking"/>
                            </label>
                        </div>
                        <div class="radio-button">
                            <label><input type="radio" id="recurrent-update-booking" name="radio-check">
                                <spring:message code="recurrent.allSeries"/>
                            </label>
                        </div>

                        <button type="button" class="btn btn-success btn-edit-event-booking"
                                id="confirm-choose-booking">
                            <spring:message code="ok"/>
                        </button>
                        <button type="button" class="btn btn-danger  btn-edit-event-booking pull-right"
                                id="close-choose">
                            <spring:message code="cancel"/>
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
                data-target=".bs-modal-lg-colourInfo">
            <span class="glyphicon glyphicon-info-sign"> </span>
        </button>
        <button type="button" class="btn btn-success btn-responsive" id="create-new-booking">
            <spring:message code="booking.makeBooking"/>
        </button>
        <button type="button" class="btn btn-success btn-responsive pull-right" data-toggle="modal"
                data-target=".bs-modal-lg-contact">
            <spring:message code="booking.contact"/>
        </button>
        <button type="button" class="btn btn-success btn-responsive pull-right"
                id="personal-discount">
            <spring:message code="administrator.personalDiscount"/>
        </button>
        <button type="button" class="btn btn-success btn-responsive pull-right"
                id="my-abonnements">
            <spring:message code="administrator.myAbonnements"/>
        </button>

        <div id="colorDescryptionModal" class="modal fade bs-modal-lg-colourInfo" tabindex="-1" role="dialog"
             aria-labelledby="myLargeModalLabel">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-content">
                        <div class="modal-body">
                            <div align="center">
                                <br>
                                <div class="col-xs-3">
                                    <h1><span style="vertical-align:middle ;color: #4CAF50;"
                                              class="glyphicon glyphicon-stop"></span></h1> <spring:message
                                        code="yourBooking"/>
                                </div>
                                <div class="col-xs-3">
                                    <h1><span style="vertical-align:middle ;color: #FFFF00;"
                                              class="glyphicon glyphicon-stop"></span></h1> <spring:message
                                        code="dayDiscount"/>
                                </div>
                                <div class="col-xs-3">
                                    <h1><span style="vertical-align:middle ;color: #EEEEEE;"
                                              class="glyphicon glyphicon-stop"></span></h1> <spring:message
                                        code="room"/>
                                </div>
                                <div class="col-xs-3">
                                    <h1><span style="vertical-align:middle ;color: #ff0000;"
                                              class="glyphicon glyphicon-stop"></span></h1> <spring:message
                                        code="bookedRoom"/>
                                </div>
                                <div class="col-xs-12">
                                    <h3>
                                            <span style="vertical-align:middle ;color: #d3af37;"
                                                  class="glyphicon glyphicon-stop"></span>
                                        <span style="vertical-align:middle ;color: #84fff7;"
                                              class="glyphicon glyphicon-stop"></span>
                                        <span style="vertical-align:middle ;color: #4CAF50;"
                                              class="glyphicon glyphicon-stop"></span>
                                        <span style="vertical-align:middle ;color: #f98e2e;"
                                              class="glyphicon glyphicon-stop"></span>
                                        <span style="vertical-align:middle ;color: #636363;"
                                              class="glyphicon glyphicon-stop"></span>
                                    </h3>
                                    <h3><span style="vertical-align:middle ;color: #1ba1e2;"
                                              class="glyphicon glyphicon-stop"></span>
                                        <span style="vertical-align:middle ;color: #044d92;"
                                              class="glyphicon glyphicon-stop"></span>
                                        <span id="loadEaster" style="vertical-align:middle ;color: #9b3aa1;"
                                              class="glyphicon glyphicon-stop"></span>
                                        <span style="vertical-align:middle ;color: #ffcd5c;"
                                              class="glyphicon glyphicon-stop"></span>
                                        <span style="vertical-align:middle ;color: #eb6f63;"
                                              class="glyphicon glyphicon-stop"></span></h3>
                                    <spring:message code="events"/>
                                    <br>
                                    <body class="kidsInfo" onload="init();">
                                    <canvas id="blob" width="600" height="400" hidden></canvas>
                                    <br>
                                    </body>
                                    <button id="closeColorDesc" class="btn btn-success center-block "
                                            data-dismiss="modal">
                                        <spring:message code="close"/></button>
                                </div>
                                <span id="softServeInc">SoftServe Inc</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="contactModal" class="modal fade bs-modal-lg-contact" tabindex="-1" role="dialog"
             aria-labelledby="myLargeModalLabel">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-body">
                        <div align="center">
                            <br>
                            <h4 id="showRoomManagers"><spring:message code="manager"/> : </h4>
                            <H4 id="roomPhone"></H4>
                            <button type="button" id="closeContact" class="btn btn-success center-block "
                                    data-dismiss="modal">
                                <spring:message code="close"/></button>
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
                <div id="confirmation-dialog-div" class="ui-dialog" title=
                <spring:message code="booking.confirmTitle"/> hidden>
                    <form id="confirm-your-choice">
                        <div class="confirmDelete">
                            <p>
                                <spring:message code="booking.confirmCancelQuestion"/>
                            </p>
                        </div>
                        <div>
                            <button type="button" class="btn btn-success btn-delete-event-booking"
                                    id="confirmYes">
                                <spring:message code="booking.confirmYes"/>
                            </button>
                            <button type="button" class="btn btn-danger btn-delete-event-booking pull-right"
                                    id="confirmNo">
                                <spring:message code="booking.confirmNo"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/resources/js/lib/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/additional-methods.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/jquery.dataTables.min.js"
        type="text/javascript"></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/jquery-ui.min.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/userCalendar.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/moment.min.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/jquery.timepicker.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/fullcalendar.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/seriousColorLegendUpdate.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/constants/manager-create-events-constants.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/validation/event-validator.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/validation/user-create-booking-validator.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/single-booking.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/renderCalendar.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/validation/eventValidator.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/validation/recurrent-cancel-validator.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/header-user.js'></script>
<script src="${pageContext.request.contextPath}/resources/js/pickers.js"></script>