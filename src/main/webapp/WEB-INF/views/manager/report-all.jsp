<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/j_spring_security_check" var="allURL" />

<link rel='stylesheet' href='resources/css/report.css'>
<link rel='stylesheet' href='resources/css/bookings.css'>

    <div class="tableDiv" id="generated">
        <table>

            <caption>
                <h2>
                    <spring:message code="report.allBookings" /></br>
                    <span id="date">(${dateThen} - ${dateNow})</span>
                </h2>
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
        <input type="button" value=<spring:message code="report.download" /> class="btn-primary exportButton">
    </div>