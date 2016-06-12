<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="reportURL" />

<link rel='stylesheet' href='resources/css/report.css'>
<link rel='stylesheet' href='resources/css/bookings.css'>

<script src="resources/js/report.js"></script>
<script src="resources/js/changeroom.js"></script>
<script src="resources/js/jquery.redirect.js"></script>

    <div id="startDateDiv">
        <h3><spring:message code="report.from" /></h3>
        <input type="date" value="${startDate}" id="startDate">
    </div>

    <div id="endDateDiv">
        <h3><spring:message code="report.to" /></h3>
        <input type="date" value="${endDate}" id="endDate">
    </div>

    <div class="tableDiv">
        <table id="activeUsers">

            <caption>
                <h3>
                    <spring:message code="report.activeParents" /></br>
                </h3>
            </caption>

            <tr>
                <th><spring:message code="report.name" /></th>
                <th><spring:message code="report.surname" /></th>
                <th><spring:message code="report.email" /></th>
                <th><spring:message code="report.phone" /></th>
                <th><spring:message code="report.bookings" /></th>
            </tr>

        </table>
    </div>

    <div id="generateButton">
        <button id="generate" class="btn-primary"><spring:message code="report.generate" /></button>
    </div>