<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>


<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/button-styles.css">


<div class="for-table">
    <table class="col-sm-offset-4 col-sm-3 reg-form">

        <tr>
            <th>
                <strong class="title-font"><spring:message code="administrator.updateRoom"/></strong>
            </th>
        </tr>

        <tr>
            <td>
                <form:form modelAttribute="<%=AdminConstants.ATR_ROOM%>" action="adm-update-room" method="post"
                           id="roomForm">

                    <form:hidden path="id" value="${room.id}"/>
                    <form:hidden path="rate" value="${room.rate}" id="rates-json"/>
                    <form:hidden path="managers" value="${room.managers}" id="managers-json"/>
                    <form:hidden path="active" value="${room.active}"/>

                    <div class="form-group sizing-between">
                        <label for="name" class="required"><spring:message code="administrator.room.name"/></label>
                        <form:input path="name" value="${room.name}" class="form-control"/>
                        <form:errors path="name" cssClass="error"/>
                    </div>

                    <div class="form-group sizing-between">
                        <label for="address" class="required"><spring:message
                                code="administrator.room.address"/></label>
                        <form:input path="address" value="${room.address}" class="form-control"/>
                        <form:errors path="address" cssClass="error"/>
                    </div>

                    <div class="form-group sizing-between">
                        <label for="city" class="required"><spring:message code="administrator.room.city"/></label>
                        <form:input path="city" value="${room.city}" class="form-control"/>
                        <form:errors path="city" cssClass="error"/>
                    </div>

                    <div class="form-group sizing-between">
                        <label for="phoneNumber" class="required"><spring:message
                                code="administrator.phoneNumber"/></label>
                        <form:input path="phoneNumber" value="${room.phoneNumber}" class="form-control"/>
                        <form:errors path="phoneNumber" cssClass="error"/>
                    </div>

                    <div class="form-group sizing-between">
                        <label for="capacity" class="required"><spring:message
                                code="administrator.room.capacity"/></label>
                        <form:input path="capacity" value="${room.capacity}" class="form-control"/>
                        <form:errors path="capacity" cssClass="error"/>
                    </div>

                    <div class="form-group sizing-between">
                        <label class="for-field1">
                            <label for="workingHoursStart" class="required">
                                <spring:message code="administrator.room.workingHoursStart"/></label>
                            <form:input type="time" path="workingHoursStart" value="${room.workingHoursStart}"
                                        class="form-control" required="required"/>
                            <form:errors path="workingHoursStart" cssClass="error"/>
                        </label>

                        <label class="for-field2">
                            <label for="workingHoursEnd" class="required">
                                <spring:message code="administrator.room.workingHoursEnd"/></label>
                            <form:input type="time" path="workingHoursEnd" value="${room.workingHoursEnd}"
                                        class="form-control" required="required"/>
                            <form:errors path="workingHoursEnd" cssClass="error"/>
                        </label>

                        <br><form:errors path="timeStartEnd" cssClass="error"/>
                    </div>

                    <div ng-app="angularjs-starter" ng-controller="MainCtrl">
                        <div class="form-group sizing-between">
                            <label class="for-field"><spring:message code="administrator.room.manager"/></label>
                            <fieldset data-ng-repeat="manager in managers">
                                <label class="for-field">
                                    <select class="form-control" ng-model="manager.id" managerId=""
                                            placeholder="manager">
                                        <c:forEach var="manager" items="${managerList}">
                                            <option value="${manager.id}">${manager.firstName} ${manager.lastName}</option>
                                        </c:forEach>
                                    </select>
                                </label>
                            </fieldset>
                            <br>
                            <button type="button" class="btn btn-success btn-lg btn-rate-manager"
                                    data-toggle="tooltip" data-placement="top"
                                    <spring:message code ="administrator.addManager" var="addManager"/>
                                    title="${addManager}" ng-click="addNewManager()">
                                <span class="glyphicon glyphicon-plus"></span>
                            </button>
                            <button type="button" class="btn btn-warning btn-lg btn-rate-manager"
                                    data-toggle="tooltip" data-placement="top"
                                    <spring:message code="administrator.removeManager" var="removeManager"/>
                                    title="${removeManager}" ng-click="removeManager()">
                                <span class="glyphicon glyphicon-minus"></span>
                            </button>
                            <br>
                            <form:errors path="managers" cssClass="error"/>
                        </div>

                        <div class="form-group sizing-between">
                            <div class="header-rate">
                                <label class="for-field"><spring:message code="administrator.room.rate"/></label>
                                <label class="for-field1"><spring:message
                                        code="administrator.room.rate.hourRate"/></label>
                                <label class="for-field2"><spring:message
                                        code="administrator.room.rate.priceRate"/></label>
                            </div>
                            <fieldset data-ng-repeat="rate in rates">
                                <label class="for-field1">
                                    <spring:message code="administrator.room.rate.hourRate" var="hourRatePlaceHolder"/>
                                    <input id="myText" type="text" ng-model="rate.hourRate" hourRate=""
                                           class="form-control"
                                           placeholder="${hourRatePlaceHolder}"/>
                                </label>
                                <label class="for-field2">
                                    <input id="myText" type="text" ng-model="rate.priceRate" priceRate=""
                                           class="form-control"
                                            <spring:message code="administrator.room.rate.priceRate" var="priceRatePlaceHolder"/>
                                           placeholder="${priceRatePlaceHolder}"/>
                                </label>
                            </fieldset>
                            <br>
                            <button type="button" class="btn btn-success btn-lg btn-rate-manager"
                                    data-toggle="tooltip" data-placement="top"
                                    <spring:message code="administrator.addRate" var="addRate"/>
                                    title="${addRate}" ng-click="addNewRate()">
                                <span class="glyphicon glyphicon-plus"></span>
                            </button>
                            <button type="button" class="btn btn-warning btn-lg btn-rate-manager"
                                    data-toggle="tooltip" data-placement="top"
                                    <spring:message code="administrator.removeRate" var="removeRate"/>
                                    title="${removeRate}" ng-click="removeRate()">
                                <span class="glyphicon glyphicon-minus"></span>
                            </button>
                            <br>
                            <form:errors path="rate" cssClass="error"/>
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn btn-raised btn-success" ng-click="submit()">
                                <spring:message code="administrator.save"/>
                            </button>
                            <button type="reset" class="btn btn-raised btn-danger"
                                    onclick="window.location.href='adm-room'">
                                <spring:message code="administrator.canc"/>
                            </button>
                        </div>
                    </div>
                    <script src="${pageContext.request.contextPath}/resources/js/room-dynamic-managers-rates.js"></script>
                </form:form>
            </td>
        </tr>

    </table>
</div>

<script src="${pageContext.request.contextPath}/resources/js/lib/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/additional-methods.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/validation/validation-room.js"></script>
