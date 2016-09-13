<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="reportURL" />

<link rel='stylesheet' href='resources/css/report.css'>

<script src="resources/js/report.js"></script>
<script src="resources/js/pagination.js"></script>
<script src="resources/js/header-manager.js"></script>

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
        <table id="bookings">

            <caption>
                <h2>
                    <spring:message code="report.activeParents" /></br>
                </h2>
            </caption>

            <tr id="header">
                <th><spring:message code="report.name" /></th>
                <th><spring:message code="report.surname" /></th>
                <th><spring:message code="report.email" /></th>
                <th><spring:message code="report.phone" /></th>
                <th><spring:message code="report.bookings" /></th>
            </tr>

        </table>
    </div>

    <div>
        <button id="generate" class="btn btn-raised btn-primary waves-effect waves-light">
        <spring:message code="report.generate" /></button>
    </div>
    <input type="hidden" id="localizedDetails" value=<spring:message code="report.details"/> />
    <input id="itemsPerPage" type="hidden" value="10" />
    <a id="dlink"  style="display:none;"></a>
</div>