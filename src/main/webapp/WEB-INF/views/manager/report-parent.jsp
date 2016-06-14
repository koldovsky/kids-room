<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="parentURL" />

<link rel='stylesheet' href='resources/css/report.css'>
<link rel='stylesheet' href='resources/css/disable-room-picker.css'>

<div id="scroller">
    <div class="dateSelector">
        <div id="from-div">
            <h3><spring:message code="report.from" /></h3>
            <input disabled type="date" value="${startDate}">
        </div>

        <div id="to-div">
            <h3><spring:message code="report.to" /></h3>
            <input disabled type="date" value="${endDate}">
        </div>
    </div>

    <div class="tableDiv">
        <table>

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
                <td>${booking.getSum()}</td>
            </tr>
            </c:forEach>

            <caption class="captionBottom">
                <h3>
                    <spring:message code="report.sumTotal" /> ${sumTotal}
                </h3>
            </caption>

        </table>
    </div>

    <div>
        <button class="btn btn-raised btn-primary waves-effect waves-light">
        <spring:message code="report.download" /></button>
    </div>
</div>