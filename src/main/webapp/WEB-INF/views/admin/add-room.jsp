<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="resources/css/button-styles.css">


<body>

<div class=" for-table">
    <table class="col-sm-offset-3 col-sm-3 reg-form">

        <tr><th>
            <strong class="title-font"><spring:message code="administrator.addRoom"/></strong>
        </th></tr>

        <tr><td>

            <form:form class="container for-table" action="adm-add-room" method="post" modelAttribute="<%=AdminConstants.ATR_ROOM%>"
                       id="roomForm">

                <form:hidden path="rate" id="rates-json"/>
                <form:hidden path="managers" id="managers-json"/>

                <div class="form-group sizing-between">
                    <label for="name" class="required"><spring:message code="administrator.room.name"/></label>
                    <form:input path="name" class="form-control" />
                    <form:errors path="name" cssClass="error"/>
                </div>

                <div class="form-group sizing-between">
                    <label for="address" class="required"><spring:message code="administrator.room.address"/></label>
                    <form:input path="address" class="form-control" />
                    <form:errors path="address" cssClass="error" />
                </div>

                <div class="form-group sizing-between">
                    <label for="city" class="required"><spring:message code="administrator.room.city"/></label>
                    <form:input path="city" class="form-control"/>
                    <form:errors path="city" cssClass="error"/>
                </div>

                <div class="form-group sizing-between">
                    <label for="phoneNumber" class="required"><spring:message code="administrator.phoneNumber"/></label>
                    <form:input path="phoneNumber" class="form-control"/>
                    <form:errors path="phoneNumber" cssClass="error"/>
                </div>

                <div class="form-group sizing-between">
                    <label for="capacity" class="required"><spring:message code="administrator.room.capacity"/></label>
                    <form:input path="capacity" type="number" class="form-control"/>
                    <form:errors path="capacity" cssClass="error"/>
                </div>

                <div class="form-group sizing-between">
                    <label class="for-field1">
                        <label for="workingHoursStart" class="required">
                            <spring:message code="administrator.room.workingHoursStart"/></label>
                        <form:input type="time" path="workingHoursStart" class="form-control"/>
                        <form:errors path="workingHoursStart" cssClass="error"/>
                    </label>

                    <label class="for-field2">
                        <label for="workingHoursEnd" class="required">
                            <spring:message code="administrator.room.workingHoursEnd"/></label>
                        <form:input type="time" path="workingHoursEnd" class="form-control"/>
                        <form:errors path="workingHoursEnd" cssClass="error"/>
                    </label>

                    <br><form:errors path="timeStartEnd" cssClass="error"/>
                </div>

                <div ng-app="angularjs-starter" ng-controller="MainCtrl">
                    <div class="form-group sizing-between">
                        <label class="for-field"><spring:message code="administrator.room.manager"/></label>
                        <fieldset  data-ng-repeat="manager in managers">
                            <label class="for-field">
                                <select class="form-control" ng-model="manager.id" managerId="" placeholder="manager">
                                    <c:forEach var="manager" items="${managerList}">
                                        <option value="${manager.id}">${manager.firstName} ${manager.lastName}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </fieldset>
                        <br>
                        <button type="button" ng-click="removeManager()">-</button>
                        <button type="button" ng-click="addNewManager()" >+</button>
                        <br>
                        <form:errors path="managers" cssClass="error"/>
                    </div>

                    <div class="form-group sizing-between">
                        <label class="for-field"><spring:message code="administrator.room.rate"/></label>
                        <label class="for-field1"><spring:message code="administrator.room.rate.hourRate"/></label>
                        <label class="for-field3"><spring:message code="administrator.room.rate.priceRate" /></label>

                        <fieldset  data-ng-repeat="rate in rates">
                            <label class="for-field1">
                                <input type="number" ng-model="rate.hourRate" hourRate="" class="form-control"
                                       placeholder=<spring:message code="administrator.room.rate.hourRate"/> />
                            </label>
                            <label class="for-field2">
                                <input type="number" ng-model="rate.priceRate" priceRate="" class="form-control"
                                       placeholder=<spring:message code="administrator.room.rate.priceRate" /> />
                            </label>
                        </fieldset>
                        <br>
                        <button type="button" ng-click="removeRate()">-</button>
                        <button type="button" ng-click="addNewRate()">+</button>
                        <br>
                        <form:errors path="rate" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <button type="submit" class="button button-confirm" ng-click="submit()">OK</button>
                        <button type="reset" class="button button-cancel"
                                onclick="window.location.href='adm-edit-room'">Cancel</button>
                    </div>
                </div>

                <script src="resources/js/room-dynamic-managers-rates.js"></script>
            </form:form>
        </td></tr>

    </table>
</div>

</body>

<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<c:if test="${pageContext.response.locale=='ua'}">
    <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/localization/messages_uk.js"></script>
</c:if>
<script src="resources/js/validation-room.js"></script>
