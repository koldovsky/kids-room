<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/j_spring_security_check" var="reportURL" />

<link rel='stylesheet' href='resources/css/report.css'>
<link rel='stylesheet' href='resources/css/bookings.css'>
<script src="resources/js/statistics.js"></script>


    <div id="dateThenDiv">
        <h2><spring:message code="report.from" /></br>
        <input type="date" value="${dateThen}" name="dateThen" id="dateThenInput"></h2>
    </div>

    <div id="dateNowDiv">
        <h2><spring:message code="report.to" /></br>
        <input type="date" value="${dateNow}" name="dateNow" id="dateNowInput"></h2>
    </div>

    <div class="tableDiv" id="statistics">
        <table id="activeUsers">

            <caption>
                <h2>
                    <spring:message code="statistics.rooms" /></br>
                    <span id="date">(${dateThen} - ${dateNow})</span>
                </h2>
            </caption>

            <tr>
                <th><spring:message code="statistics.name" /></th>
                <th><spring:message code="statistics.city" /></th>
                <th><spring:message code="statistics.address" /></th>
                <th><spring:message code="statistics.manager" /></th>
                <th><spring:message code="statistics.sum" /></th>
            </tr>

            <c:forEach var="room" items="${statistics.keySet()}">
            <tr>
                <td>${room.name}</td>
                <td>${room.city}</td>
                <td>${room.address}</td>
                <td>${room.manager}</td>
                <td>${statistics.get(room)}</td>
            </tr>
            </c:forEach>

        </table>
    </div>