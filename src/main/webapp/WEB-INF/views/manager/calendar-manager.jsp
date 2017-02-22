<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link href='${pageContext.request.contextPath}/resources/css/fullcalendar.css' rel='stylesheet'/>
<link href='${pageContext.request.contextPath}/resources/css/fullcalendar.print.css' rel='stylesheet' media='print'/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">

<link href='${pageContext.request.contextPath}/resources/css/formForCalendar.css' rel='stylesheet'/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery.timepicker.css"/>
<link href='${pageContext.request.contextPath}/resources/css/flow-form.css' rel='stylesheet'/>
<link href='${pageContext.request.contextPath}/resources/css/manager-no-rooms.css' rel='stylesheet'/>

<link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/dataTables.bootstrap4.min.css"/>

<div class="loading" hidden>Loading&#8230;</div>
<c:forEach items="${managersRoom}" var="r">
    ${r.id}
</c:forEach>
<c:if test="${rooms.isEmpty()}">
    <div id="manager-no-rooms">
        <h1>
          <span class="label label-warning" id="radius">
          <spring:message code="manager.oops"/></span>
        </h1>
        <h3 class="center"><spring:message code="manager.noRooms"/></h3>
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
                            <label for="recurrent-event-start-date"><spring:message
                                    code="booking.startDate"/></label>
                            <br>
                            <div class="col-xs-6">
                                <input type="date" class="form-control" id="recurrent-event-start-date"
                                       placeholder="startDate">
                            </div>
                            <div class="col-xs-5">
                                <input id="recurrent-event-start-time" type="text"
                                       class="time form-control timepicker"
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
                                <input id="recurrent-event-end-time" type="text"
                                       class="time form-control timepicker"
                                       size="6"/>
                            </div>
                        </div>
                        <br><br>
                        <label for="recurrent-event-description"><spring:message
                                code="recurrent.description"/></label>
                            <textarea class="form-control" id="recurrent-event-description"
                                      placeholder="description" cols="30"></textarea>
                        <div class="col-xs-6">
                            <button type="button" class="btn btn-success" id="recurrent-event-create">
                                <spring:message code="booking.create"/></button>
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
                        <div class="form-group">
                            <label for="event-title"><spring:message code="event.title"/></label>
                            <spring:message code="event.titlePlaceHolder" var="titlePlaceHolder"/>
                            <input type="text" class="form-control" id="event-title"
                                   placeholder="${titlePlaceHolder}">
                        </div>
                        <div class="form-group">
                            <label for="color-select"><spring:message code="event.chooseColor"/></label>
                            <select id="color-select">
                                <option value="#eb6f63" style="background:red">
                                    <spring:message code="color.red"/>
                                </option>
                                <option value="#ffcd5c" style="background:yellow">
                                    <spring:message code="color.yellow"/>
                                </option>
                                <option value="#9b3aa1" style="background:purple">
                                    <spring:message code="color.purple"/>
                                </option>
                                <option value="#044d92" style="background:blue">
                                    <spring:message code="color.blue"/>
                                </option>
                                <option value="#1ba1e2" style="background:#6AA4C1" selected="selected">
                                    <spring:message code="color.lightBlue"/>
                                </option>
                                <option value="#636363" style="background:grey">
                                    <spring:message code="color.grey"/>
                                </option>
                                <option value="#51d466" style="background:green">
                                    <spring:message code="color.green"/>
                                </option>
                                <option value="#f98e2e" style="background:orange">
                                    <spring:message code="color.orange"/>
                                </option>
                                <option value="#84fff7" style="background:aqua">
                                    <spring:message code="color.aqua"/>
                                </option>
                                <option value="#d3af37" style="background:gold">
                                    <spring:message code="color.gold"/><br></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="start-date-picker"><spring:message code="event.startDate"/></label>
                            <br>
                            <div class="col-xs-6">
                                <input id="start-date-picker" type="date" class="text-center form-control"
                                       placeholder="startDate">
                            </div>
                            <div class="col-xs-6">
                                <input id="start-time-picker" type="text"
                                       class=" text-center time form-control timepicker" size="6"/>
                            </div>
                        </div>
                        <br>

                        <div class="form-group">
                            <label for="end-date-picker"><spring:message code="event.endDate"/></label>
                            <br>
                            <div class="col-xs-6">
                                <input id="end-date-picker" type="date" class="text-center form-control"
                                       placeholder="endDate"
                                       disabled="true">
                            </div>
                            <div class="col-xs-6">
                                <input id="end-time-picker" type="text"
                                       class="text-center time form-control timepicker" size="6"/>
                            </div>
                        </div>


                        <div class="col-xs-12">
                            <div class="row">
                                <form role="form">
                                    <div class="row col-xs-4">
                                        <br>
                                        <div class="radio-button">
                                            <label><input type="radio" name="optradio"
                                                          id="single-event-radio-button"
                                                          class="my-radio" checked>
                                                <spring:message code="event.singleEvent"/></label>
                                        </div>
                                        <div class="radio-button">
                                            <label><input type="radio" name="optradio" id="weekly-radio-button"
                                                          class="my-radio">
                                                <spring:message code="event.weeklyEvent"/></label>
                                        </div>
                                        <div class="radio-button">
                                            <label><input type="radio" name="optradio" id="monthly-radio-button"
                                                          class="my-radio">
                                                <spring:message code="event.monthlyEvent"/></label>
                                        </div>
                                    </div>
                                    <div class="row col-xs-7" id="days-for-monthly-form" hidden>
                                        <br><spring:message code="event.checkRequiredMonth"/>
                                        <table class="table center" id="monthly-days">
                                        </table>
                                    </div>
                                    <div class="row col-xs-9" id="days-for-recurrent-form" hidden>
                                        <table class="table" id="days-for-recurrent">
                                            <br>
                                            <thead><spring:message code="event.checkRequiredWeeks"/></thead>
                                            <tbody>
                                            <tr>
                                                <td><label><input type="checkbox" id="Monday" value="Mon"
                                                                  class="day">
                                                    <spring:message code="monday"/></label><br>
                                                </td>
                                                <td><label><input type="checkbox" id="Tuesday" value="Tue"
                                                                  class="day">
                                                    <spring:message code="tuestay"/></label><br>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label><input type="checkbox" id="Wednesday" value="Wed"
                                                                  class="day"> <spring:message
                                                        code="wednesday"/></label><br>
                                                </td>
                                                <td><label><input type="checkbox" id="Thursday" value="Thu"
                                                                  class="day">
                                                    <spring:message code="thursday"/></label><br>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><label><input type="checkbox" id="Friday" value="Fri"
                                                                  class="day">
                                                    <spring:message code="friday"/></label><br>
                                                </td>
                                                <td><label><input type="checkbox" id="Saturday" value="Sat"
                                                                  class="day">
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
                                    <spring:message code="event.descriptionPlaceHolder"
                                                    var="descriptionPlaceHolder"/>
                                    <label for="description"> <spring:message
                                            code="event.labelForDescription"></spring:message> </label>
                                    <input type="text" size="15" class="form-control" id="description"
                                           placeholder="${descriptionPlaceHolder}">
                                </div>
                                <br>
                                <div id="data-validation-information-string-container" class="clearfix">
                                    <p class="col-xs-12 data-validation-information-string" style="color:red"
                                       id="data-validation-information-string"></p>
                                </div>
                                <br>
                                <div class="clearfix"></div>
                                <div class="col-xs-3">
                                    <button type="button" class="btn btn-success" id="update-recurrent-button"
                                            hidden="true">
                                        <spring:message code="booking.update"/>
                                    </button>
                                    <button type="button" class="btn btn-success live" id="create-button">
                                        <spring:message code="booking.create"/></button>
                                </div>
                                <div align="right" class="col-xs-9">
                                    <button type="button" class="btn btn-danger pull-right" id="cancel">
                                        <spring:message code="cancel"/></button>
                                </div>
                                <div class="col-xs-12">
                                    <footer class="deleteBookingButtonLink">
                                        <div id="deleting-recurrent-event"
                                             style="text-decoration: underline; text-align: center;">
                                            <spring:message code="event.cancelSeriesOfEvents"/>
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
                <div id="updating" class="dialog" hidden
                     title="<spring:message code="event.changeEvent"></spring:message>">
                    <form id="updatingForm">

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <label for="titleUpdate"> <spring:message
                                    code="event.labelForTitle"></spring:message> </label>
                            <spring:message code="event.titlePlaceHolder" var="titlePlaceHolder"/>
                            <input type="text" class="form-control" id="titleUpdate"
                                   placeholder="${titlePlaceHolder}">
                        </div>

                        <div class="form-group">
                            <label for="color-select"> <spring:message
                                    code="event.chooseColor"></spring:message> </label>
                            <select id="color-select-single-event">
                                <option value="#eb6f63" style="background:red">
                                    <spring:message code="color.red"/>
                                </option>
                                <option value="#ffcd5c" style="background:yellow">
                                    <spring:message code="color.yellow"/>
                                </option>
                                <option value="#9b3aa1" style="background:purple">
                                    <spring:message code="color.purple"/>
                                </option>
                                <option value="#044d92" style="background:blue">
                                    <spring:message code="color.blue"/>
                                </option>
                                <option value="#1ba1e2" style="background:#6AA4C1" selected="selected">
                                    <spring:message code="color.lightBlue"/>
                                </option>
                                <option value="#636363" style="background:grey">
                                    <spring:message code="color.grey"/>
                                </option>
                                <option value="#51d466" style="background:green">
                                    <spring:message code="color.green"/>
                                </option>
                                <option value="#f98e2e" style="background:orange">
                                    <spring:message code="color.orange"/>
                                </option>
                                <option value="#84fff7" style="background:aqua">
                                    <spring:message code="color.aqua"/>
                                </option>
                                <option value="#d3af37" style="background:gold">
                                    <spring:message code="color.gold"/><br></option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="startDayUpdate"> <spring:message
                                    code="event.startDate"></spring:message> </label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="startDayUpdate" placeholder="startDate"
                                       readonly>
                            </div>
                            <div class="col-xs-6">
                                <input id="startTimeUpdate" type="text"
                                       class="text-center time form-control timepicker" size="6"/>
                            </div>
                        </div>
                        <br>

                        <div class="form-group">
                            <label for="endDateUpdate"> <spring:message
                                    code="event.endDate"></spring:message> </label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="endDateUpdate" placeholder="endDate"
                                       readonly>
                            </div>
                            <div class="col-xs-6">
                                <input id="endTimeUpdate" type="text"
                                       class="text-center time form-control timepicker" size="6"/>
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
                            <p class="col-xs-12 data-validation-information-string" style="color:red"
                               id="data-validation-information-string"></p>
                        </div>
                        <br>
                        <button type="button" class="btn btn-success" id="updatingButton">
                            <spring:message code="event.update"></spring:message>
                        </button>
                        <button type="button" class="pull-right btn btn-danger" id="cancel-update">
                            <spring:message code="cancel"></spring:message>
                        </button>

                        <div class="col-xs-12">
                            <footer class="deleteEventButtonLink">
                                <div id="deleting-single-event"
                                     style="text-decoration: underline; text-align: center;">
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
                <div id="choose-updating-type" title="<spring:message
                            code="recurrent.event.title"/>" class="dialog" hidden>
                    <form id="choose-updating-form">
                        <div class="lableBoard">
                            <label class="small">
                                <spring:message code="recurrent.editMessage"/>
                            </label>
                        </div>
                        <div class="radio-button">
                            <label><input type="radio" id="single-update" name="radio" checked> <spring:message
                                    code="recurrent.justThisOneEvent"/></label>
                        </div>
                        <div class="radio-button">
                            <label><input type="radio" id="recurrent-update" name="radio"> <spring:message
                                    code="recurrent.allSeries"/></label>
                        </div>
                        <button type="button" class="btn btn-success btn-edit-event-booking" id="confirm-choose">
                            <spring:message code="ok"/></button>
                        <button type="button" class="btn btn-danger btn-edit-event-booking pull-right" id="cancel-choose">
                            <spring:message code="cancel"/></button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <button type="button" class="btn btn-success" id="create-new-event"><spring:message
                code="event.new"/></button>
        <div id='calendar'></div>
    </div>
</c:if>

<%--confirmation-dialog--%>
<div class="row">
    <div class="modal-dialog modal-lg vertical-center-row ">
        <div align="center">
            <div id="confirmation-dialog-event-div" class="ui-dialog" title=
            <spring:message code="event.confirmTitle"/> hidden>
                <form id="confirm-your-choice-event">
                    <div class="confirmDelete">
                        <p>
                            <spring:message code="event.confirmCancelQuestion"/>
                        </p>
                    </div>
                    <div>
                        <button type="button" class="btn btn-success btn-delete-event-booking" id="confirmYesEvent">
                            <spring:message code="event.confirmYes"/>
                        </button>
                        <button type="button" class="btn btn-danger btn-delete-event-booking pull-right"
                                id="confirmNoEvent">
                            <spring:message code="event.confirmNo"/>
                        </button>
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
                        <p style="color:red;"><spring:message code="cancel.warning"
                                                              arguments='<span id="event-cancel-title"></span>'/></p>
                    </div>
                    <br>
                    <div class="form-group">
                        <label align-items="center" for="start-date-cancel-picker"><spring:message
                                code="event.startDate"/></label>
                        <div>
                            <input id="start-date-cancel-picker" type="date" class="form-control"
                                   placeholder="startDate">
                        </div>
                    </div>

                    <div class="form-group">
                        <label align="center" for="end-date-cancel-picker"><spring:message
                                code="event.endDate"/></label>
                        <br>
                        <div>
                            <input id="end-date-cancel-picker" type="date" class="form-control"
                                   placeholder="endDate">
                        </div>
                    </div>

                    <div class="col-xs-12">
                        <div class="row">
                            <div class="clearfix"></div>
                            <br>
                            <div id="validation-information-container" class="clearfix">
                                <p class="col-xs-12 data-validation-information-string"
                                   style="color:red;" id="validation-information"></p>
                            </div>
                            <br>
                            <div class="clearfix"></div>
                            <div class="col-xs-6" style="text-align:center;">
                                <button type="button" class="btn btn-success btn-block"
                                        id="yes-cancel-event-button"><spring:message code="yes"/></button>
                            </div>
                            <div class="col-xs-6" style="text-align:center;">
                                <button type="button" class="btn btn-danger btn-block"
                                        id="no-cancel-event-button"><spring:message code="no"/></button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src='${pageContext.request.contextPath}/resources/js/header-manager.js'></script>

<div id="error-dialog" type="hidden"></div>
<div id="warning-dialog" type="hidden"></div>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
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
<c:choose>
    <c:when test="${pageContext.response.locale=='ua'}">
        <script src="${pageContext.request.contextPath}/resources/js/lib/callendar-ua.min.js"></script>
    </c:when>
</c:choose>
