<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="resources/css/button-styles.css">


<body>
<div class="for-table table-responsive">
    <table class="for-table table table-hover reg-form" style="width: 85%;">
        <tr class="hide-border">
            <th colspan="10" class="set-standard-color">
                <strong class="title-font"><spring:message code="administrator.listRooms"/></strong>
            </th>
        </tr>

        <tr>
            <th class="th-size"><strong><spring:message code="administrator.room.name"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.room.address"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.room.city"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.phoneNumber"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.room.capacity"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.room.roomWorkTime"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.room.manager"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.room.rate"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.edit"/></strong></th>
            <th class="th-size"><strong><spring:message code="administrator.block"/></strong></th>
        </tr>

        <c:forEach var="room" items="${roomList}">
        <c:if test="${room.active eq true}"><tr></c:if>
        <c:if test="${room.active ne true}"><tr class="tr-not-active"></c:if>
            <td>${room.name}</td>
            <td>${room.address}</td>
            <td>${room.city}</td>
            <td>${room.phoneNumber}</td>
            <td>${room.capacity}</td>
            <td>
                <table class="hide-border">
                    <tr><td><spring:message code="administrator.room.startTime"/> ${room.workingHoursStart}</td></tr>
                    <tr><td><spring:message code="administrator.room.endTime"/> ${room.workingHoursEnd}</td></tr>
                </table>
            </td>
            <td>
                <table class="hide-border">
                    <c:forEach var="manager" items="${room.managers}">
                        <tr><td >${manager.firstName} ${manager.lastName}</td></tr>
                    </c:forEach>
                </table>
            </td>
            <td>
                <table class="hide-border">
                    <tr>
                        <td><spring:message code="administrator.room.rate.hourRate"/></td>
                        <td><spring:message code="administrator.room.rate.priceRate"/></td>
                    </tr>
                    <c:forEach var="rate" items="${room.rates}">
                    <tr>
                        <td>${rate.hourRate}</td>
                        <td>${rate.priceRate}</td>
                    </tr>
                    </c:forEach>
                </table>
            </td>
            <td><a href="adm-update-room?id=${room.id}"><button class="button button-size-default edit"/></button></a></td>
            <td>
                <c:if test="${room.active ne true}">
                    <c:url var="lockUrl" value="/adm-edit-room?id=${room.id}"/>
                    <form:form id="${managerFormId}" action="${lockUrl}" method="POST" >
                        <input id="room" name="manager" type="hidden" value="${room.id}" />
                        <button type="submit" value="lock"
                                onClick="return confirm('Are you sure you want to make the room active?')"
                                class="button button-size-default delete"></button>
                    </form:form>
                </c:if>
                <c:if test="${room.active eq true}">
                    <c:url var="lockUrl" value="/adm-edit-room?id=${room.id}"/>
                    <form:form id="${managerFormId}" action="${lockUrl}" method="POST" >
                       <input id="room" name="room" type="hidden" value="${room.id}" />
                       <button type="submit" value="lock"
                               onClick="return confirm('Are you sure you want to make the room inactive?')"
                               class="button button-size-default save"></button>
                    </form:form>
                </c:if>
            </td>
         </tr>
         </c:forEach>

        <tr>
            <th colspan="10" class="hide-border set-standard-color">
                <a href="adm-add-room"><input type="button" value=<spring:message code="administrator.add"/>
                                        class="button-add button"/></a>
            </th>
        </tr>
    </table>
</div>
</body>