<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="reportURL" />

<link rel='stylesheet' href='resources/css/report.css'>

<script src="resources/js/statistics.js"></script>

<div id="scroller">
    <div class="dateSelector">
        <div id="from-div">
            <h3><spring:message code="report.from" /></h3>
            <input type="date" value="${startDate}" id="startDate">
        </div>

        <div id="to-div">
            <h3><spring:message code="report.to" /></h3>
            <input type="date" value="${endDate}" id="endDate">
        </div>
    </div>

    <div class="tableDiv">
        <table id="statistics">

            <caption>
                <h3>
                    <spring:message code="statistics.rooms" /></br>
                </h3>
            </caption>

            <tr id="header">
                <th><spring:message code="statistics.name" /></th>
                <th><spring:message code="statistics.city" /></th>
                <th><spring:message code="statistics.address" /></th>
                <th><spring:message code="statistics.managers" /></th>
                <th><spring:message code="statistics.sum" /></th>
            </tr>

        </table>
    </div>
</div>