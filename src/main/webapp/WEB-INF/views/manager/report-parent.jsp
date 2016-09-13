<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="parentURL" />

<link rel='stylesheet' href='resources/css/report.css'>
<link rel='stylesheet' href='resources/css/disable-room-picker.css'>

<script src="resources/js/pagination.js"></script>

<div id="scroller">
    <div class="dateSelector">
        <div id="from-div">
            <h3><spring:message code="report.from" /></h3>
            <input disabled type="date" value="${startDate}" id="startDate">
        </div>

        <div id="to-div">
            <h3><spring:message code="report.to" /></h3>
            <input disabled type="date" value="${endDate}" id="endDate">
        </div>
    </div>

    <div class="tableDiv">
        <table id="bookings">

            <caption>
                <h2>
                    ${parent} <spring:message code="report.in" /> ${room}
                </h2>
            </caption>

            <tr>
                <th><spring:message code="report.date" /></th>
                <th><spring:message code="report.kid" /></th>
                <th><spring:message code="report.startTime" /></th>
                <th><spring:message code="report.endTime" /></th>
                <th><spring:message code="report.duration" /></th>
                <th><spring:message code="report.sum" /></th>
            </tr>

            <c:forEach var="booking" items="${bookings}">
            <tr>
                <td><fmt:formatDate pattern="dd.MM.yyyy" value="${booking.bookingStartTime}" /></td>
                <td>${booking.child}</td>
                <td><fmt:formatDate pattern="HH:mm" value="${booking.bookingStartTime}" /></td>
                <td><fmt:formatDate pattern="HH:mm" value="${booking.bookingEndTime}" /></td>
                <td>${booking.formatDuration()}</td>
                <td><fmt:formatNumber var="bookingCost" value="${ booking.getSum() / 100}" maxFractionDigits="2" minFractionDigits="2" />${bookingCost}</td>
            </tr>
            </c:forEach>
            <caption class="captionBottom">
                <h3>
                    <spring:message code="report.sumTotal" />
                    <fmt:formatNumber var="totalSum" value="${ sumTotal / 100}" maxFractionDigits="2" minFractionDigits="2" />
                    ${totalSum}
                    <spring:message code="report.currencySymbol" />
                </h3>
            </caption>

        </table>
    </div>

    <input id="itemsPerPage" type="hidden" value="10" />
    <a id="dlink"  style="display:none;"></a>
    <button id="export" onclick= "tableToExcel('bookings', 'name')" class="btn btn-raised btn-success waves-effectwaves-light exportButton glyphicon glyphicon-download-alt">
        &nbsp; <spring:message code="report.download" /> Excel
    </button>
</div>