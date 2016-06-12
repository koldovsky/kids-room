<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/j_spring_security_check" var="reportURL" />

<link rel='stylesheet' href='resources/css/report.css'>
<link rel='stylesheet' href='resources/css/bookings.css'>

<script src="resources/js/jquery.redirect.js"></script>

<script src="resources/js/changeroom.js"></script>
<script src="resources/js/report.js"></script>


    <div id="dateThenDiv">
        <h3><spring:message code="report.from" /></h3>
        <input type="date" value="${dateThen}" name="dateThen" id="dateThen">
    </div>

    <div id="dateNowDiv">
        <h3><spring:message code="report.to" /></h3>
        <input type="date" value="${dateNow}" name="dateNow" id="dateNow">
    </div>

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

        </table>
    </div>

    <div id="generateButton">
        <button id="generate" class="btn-primary"><spring:message code="report.generate" /></button>
    </div>