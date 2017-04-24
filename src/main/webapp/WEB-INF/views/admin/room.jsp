<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>


<script src="${pageContext.request.contextPath}/resources/js/lib/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/dataTables.bootstrap4.min.js" type="text/javascript"></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/moment.min.js' type="text/javascript"></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/jquery.timepicker.js' type="text/javascript"></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/jquery-ui.min.js'></script>



<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/lib/intlTelInput.css">
<link rel="stylesheet" href='${pageContext.request.contextPath}/resources/css/lib/jquery-ui.css'>
<link href="${pageContext.request.contextPath}/resources/css/jquery.timepicker.css" rel="stylesheet"/>
<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/flow-form.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/button-styles.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/admin-add-manager.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/admin-style.css">

<div class="container">
            <table class="for-table table table-hover reg-form" id="rooms-table">
                <caption>
                    <i class="glyphicon glyphicon-home glyphicon-room"></i>
                    <strong class="title-font"><spring:message code="administrator.listRooms"/></strong>
                </caption>
                <thead>
                <tr>
                    <th class="th-size"><strong><spring:message code="administrator.room.name"/></strong></th>
                    <th class="th-size"><strong><spring:message code="administrator.room.city"/></strong></th>
                    <th class="th-size"><strong><spring:message code="administrator.room.address"/></strong></th>
                    <th class="th-size"><strong><spring:message code="administrator.phoneNumber"/></strong></th>
                    <th class="th-size"><strong><spring:message code="administrator.room.capacity"/></strong></th>
                    <th class="th-size"><strong><spring:message code="administrator.room.roomWorkTime"/></strong></th>
                    <th class="th-size"><strong><spring:message code="administrator.room.manager"/></strong></th>
                    <th class="th-size"><strong><spring:message code="administrator.room.rate"/></strong></th>
                    <th class="th-size"><strong><spring:message code="administrator.edit"/></strong></th>
                    <th class="th-size"><strong><spring:message code="administrator.state"/></strong></th>
                </tr>
                </thead>
                <tbody>

                </tbody>
                <tfoot>
                <tr>
                    <th colspan="10" class="hide-border set-standard-color">
                       <button id="showAddRoom" class="btn btn-raised btn-primary btn-add-room">
                                <spring:message code="administrator.add"/>
                            </button>

                    </th>
                </tr>
                </tfoot>
            </table>

    <div id="deactivateModal" class="dialog" hidden >
        <p class="cancelName ">
            <spring:message code="room.deactivate"/>
        </p>

        <div id="warningMessages"></div>
        <br>
        <br>
        <form id="reasonDeactivate">
            <label for="reasonText"><spring:message code="administrator.room.deactivateReason"/></label>
            <input type="text" name="reason" id="reasonText"/>
        </form>
        <button id="deactivateYesButton" class="btn  btn-success admWarningBtn">
            <spring:message code="room.yes_button"/>
        </button>

        <button id="deactivateNoButton" class="btn btn-danger admWarningBtn" data-dismiss="modal">
            <spring:message code="room.no_button"/>
        </button>
    </div>


    <div id="activateModal" class="dialog" hidden>
        <p>
            <spring:message code="room.activate"/>
        </p>
        <button id="activateYesButton" class="btn  btn-success admWarningBtn">
            <spring:message code="room.yes_button"/>
        </button>

        <button id="activateNoButton" class="btn btn-danger admWarningBtn" data-dismiss="modal">
            <spring:message code="room.no_button"/>
        </button>

    </div>
            <div id="room-dialog " class="dialog" hidden >
                            <form class="container for-table" id="roomForm">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" autofocus/>
                                <input type="hidden" id="active" name="active" value="true"/>
                                <input type="hidden" id="id" name="id"/>
                                <div class="form-group sizing-between">
                                    <label for="name" class="required"><spring:message code="administrator.room.name"/></label>
                                    <input type="text" name="name" id="name" class="form-control"/>
                                    <errors path="name" cssClass="error"/>
                                </div>

                                <div class="form-group sizing-between">
                                    <label for="address" class="required"><spring:message code="administrator.room.address"/></label>
                                    <input type="text" id="address" name="address" class="form-control"/>
                                    <errors path="address" cssClass="error"/>
                                </div>

                                <div class="form-group sizing-between">
                                <label for="city" class="required"><spring:message code="administrator.room.city"/></label>
                                    <input type="text" id="city" name="city" class="form-control"/>
                                    <errors path="city" cssClass="error"/>
                                </div>

                                <div class="form-group sizing-between">
                                <label for="phoneNumber" class="required"><spring:message
                                    code="administrator.phoneNumber"/></label>
                                    <br>
                                    <input type="text" id="phoneNumber" name="phoneNumber" class="form-control"/>
                                    <errors path="phoneNumber" cssClass="error"/>
                                </div>

                                <div class="form-group sizing-between">
                                        <label for="capacity" class="required"><spring:message
                                                code="administrator.room.capacity"/></label>
                                        <input type="number" id="capacity" name="capacity" class="form-control"/>

                                    <errors path="capacity" cssClass="error"/>
                                    </div>

                                <div class="form-group sizing-between hours">
                                    <label class="for-field1">
                                        <label for="workingHoursStart" class="required">
                                            <spring:message code="administrator.room.workingHoursStart"/></label>
                                        <input type="text" id="workingHoursStart" name="workingHoursStart"
                                               class="form-control picker"/>
                                    </label>
                                    <label class="for-field2">
                                        <label for="workingHoursStart" class="required">
                                            <spring:message code="administrator.room.workingHoursEnd"/></label>
                                        <input type="text" id="workingHoursEnd" name="workingHoursEnd"
                                               class="form-control picker"/>
                                    </label>
                                </div>
                                <br><br>

                                <div class="form-group sizing-between" id="manager-between">
                                    <label class="required"><spring:message code="administrator.room.manager"/></label>
                                         <span id="addManager" class="btn btn-success btn-lg btn-rate-manager"
                                               title=<spring:message code="administrator.addManager"/>>
                                        <span class="glyphicon glyphicon-plus"></span>
                                        </span>
                                     <span id="removeManager" class="btn btn-danger btn-lg btn-rate-manager"
                                           title=<spring:message code="administrator.removeManager"/>>
                                        <span class="glyphicon glyphicon-minus"></span>
                                        </span>
                                        <br><br>
                                            <ul id="listManager" type="none">
                                                <li><select class="form-control manager" id="man" name="manager[]"></select></li>
                                            </ul>

                                </div>

                                <div class="form-group sizing-between rates">
                                    <label class="required" for="rates"><spring:message code="administrator.room.rate"/></label>

                                        <span id="addRate" class="btn btn-success btn-lg btn-rate-manager"
                                              title=<spring:message code="administrator.addRate"/>>

                                        <span class="glyphicon glyphicon-plus"></span>
                                    </span>

                                            <span id="removeRate" class="btn btn-danger btn-lg btn-rate-manager"
                                                  title=<spring:message code="administrator.removeRate"/>>
                                        <span class="glyphicon glyphicon-minus"></span>

                                        </span>
                                    <fieldset id="rates" name="rates">
                                        <table  class="tableRates">
                                            <tr>
                                                <th><label for="hour"><spring:message code="administrator.room.rate.hourRate"/></label></th>
                                                <th><label for="hour"><spring:message code="administrator.room.rate.priceRate"/></label></th>
                                            </tr>
                                            <tbody id="tableRates">
                                            <tr>
                                                <td>
                                                    <input type="number" name="hour" id="hour" class="form-control">
                                                </td>
                                                <td>
                                                    <input type="number" name="rate" id="rate" step="any" class="form-control">
                                                </td>
                                            </tr>
                                            </tbody>


                                        </table>
                                    </fieldset>

                                </div>
                            </form>
                                <div class="form-group buttonsDialog">
                                    <button id="btnSubmitUpdateRoom" class="btn btn-raised btn-success">
                                        <spring:message code="administrator.updateRoom"/>
                                    </button>
                                    <button id="btnSubmitNewRoom" class="btn btn-raised btn-success" disabled>
                                        <spring:message code="administrator.submit"/>
                                    </button>
                                    <button type="reset" class="btn btn-raised btn-danger" id="close-add-dialod">
                                    <spring:message code="administrator.canc"/>
                                    </button>
                                </div>

            </div>

</div>

<script src='${pageContext.request.contextPath}/resources/js/admin-room.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/admin-deactivate-room.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/validation/validation-room.js'></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/additional-methods.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/intlTelInput.min.js"></script>
<script src='${pageContext.request.contextPath}/resources/js/adminRoomSearch.js'></script>

<script src='${pageContext.request.contextPath}/resources/js/pickers.js'></script>
