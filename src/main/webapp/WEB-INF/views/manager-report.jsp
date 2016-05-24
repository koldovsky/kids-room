<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/j_spring_security_check" var="reportURL" />

<link rel='stylesheet' href='resources/css/report.css'>
<link rel='stylesheet' href='resources/css/bookings.css'>
<script src="resources/js/report.js"></script>

    <form id="generateAReport" action="manager-report-all">

        <div id="dateThenDiv">
            <h2>Choose start time</br>
            <input type="date" value="${dateThen}" name="dateThen" id="dateThenInput"></h2>
        </div>

        <div id="dateNowDiv">
            <h2>Choose end time</br>
            <input type="date" value="${dateNow}" name="dateNow" id="dateNowInput"></h2>
        </div>

    </form>

    <div class="tableDiv">
        <table id="activeUsers">

            <caption>
                <h2>
                    <spring:message code="report.activeParents" /></br>
                    <span id="date">(${dateThen} - ${dateNow})</span>
                </h2>
            </caption>

            <tr>
                <th><spring:message code="report.name" /></th>
                <th><spring:message code="report.surname" /></th>
                <th><spring:message code="report.email" /></th>
                <th><spring:message code="report.phone" /></th>
                <th><spring:message code="report.bookings" /></th>
            </tr>

            <c:forEach var="user" items="${activeUsers}">
            <tr>
                <td>${user.getFirstName()}</td>
                <td>${user.getLastName()}</td>
                <td>${user.getEmail()}</td>
                <td>${user.getPhoneNumber()}</td>
                <td class="parentRow" id="${user.getEmail()}">
                    <a><spring:message code="report.details" /></a>
                </td>
            </tr>
            </c:forEach>

        </table>
    </div>

    <div id="generateButton">
        <input type="submit" form="generateAReport"
         value=<spring:message code="report.generate" /> class="btn-primary">
    </div>

    <form action="manager-report-parent" id="allBookingsPerParentForm">
        <input type="hidden" id="parentEmailHidden" name="parentEmail"/>
        <input type="hidden" id="dateThenHidden" name="dateThen"/>
        <input type="hidden" id="dateNowHidden" name="dateNow"/>
    </form>