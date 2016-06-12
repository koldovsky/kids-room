<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="allURL" />

<link rel='stylesheet' href='resources/css/report.css'>
<link rel='stylesheet' href='resources/css/bookings.css'>
<link rel='stylesheet' href='resources/css/disable-room-picker.css'>

    <div id="startDateDiv">
        <h3><spring:message code="report.from" /></h3>
        <input disabled type="date" value="${startDate}" id="startDate">
    </div>

    <div id="endDateDiv">
        <h3><spring:message code="report.to" /></h3>
        <input disabled type="date" value="${endDate}" id="endDate">
    </div>

    <div class="tableDiv" id="generated">
        <table>

            <caption>
                <h3>
                    <spring:message code="report.allBookings" /></br>
                </h3>
            </caption>

            <tr>
                <th><spring:message code="report.parent" /></th>
                <th><spring:message code="report.email" /></th>
                <th><spring:message code="report.sum" /></th>
            </tr>

            <c:forEach var="user" items="${report.keySet()}">
            <tr>
                <td>${user}</td>
                <td>${user.getEmail()}</td>
                <td>${report.get(user)}</td>
            </tr>
            </c:forEach>

        </table>
    </div>

    <div id="exportButton">
        <button class="btn-primary exportButton"><spring:message code="report.download" /></button>
    </div>