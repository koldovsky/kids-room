<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/j_spring_security_check" var="allBookingsPerParentURL" />
<link rel='stylesheet' href='resources/css/report.css'>
<script src="resources/js/exportIntoExcel.js"></script>

    <div class="tableDiv">
        <table>
            <caption>
                <h2>
                    <spring:message code="report.parentBookings" /> ${parent}</br>
                    <span id="date">(${dateThen} - ${dateNow})</span>
                </h2>
            </caption>

            <tr>
                <th><spring:message code="report.date" /></th>
                <th><spring:message code="report.kid" /></th>
                <th><spring:message code="report.place" /></th>
                <th><spring:message code="report.startTime" /></th>
                <th><spring:message code="report.endTime" /></th>
                <th><spring:message code="report.duration" /></th>
                <th><spring:message code="report.sum" /></th>
            </tr>

            <c:forEach var="booking" items="${bookings}">
            <tr>
                <td><fmt:formatDate pattern="dd/MM" value="${booking.bookingStartTime}" /></td>
                <td>${booking.idChild}</td>
                <td>${booking.idRoom}</td>
                <td><fmt:formatDate pattern="HH:mm" value="${booking.bookingStartTime}" /></td>
                <td><fmt:formatDate pattern="HH:mm" value="${booking.bookingEndTime}" /></td>
                <td>${booking.getDuration()}</td>
                <td>${booking.getSum(booking.getDuration())}</td>
            </tr>
            </c:forEach>

            <caption class="captionBottom">
                <p>
                    <spring:message code="report.sumTotal" /> ${sumTotal}
                </p>
            </caption>

        </table>
    </div>

    <div id="exportButton">
        <input type="button" value="Export into Excel" class="btn-primary exportButton">
    </div>