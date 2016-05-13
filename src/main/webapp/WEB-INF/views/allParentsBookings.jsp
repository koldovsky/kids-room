<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/j_spring_security_check" var="allParentsBookingsURL" />

    <div class="table">
        <table>
            <caption>
            <h2><spring:message code="report.allBookings" /><span class="smallText"></br>(${dateThen} - ${dateNow})</span></h2>
            </caption>
            <tr>
                <th><spring:message code="report.parent" /></th>
                <th><spring:message code="report.date" /></th>
                <th><spring:message code="report.kid" /></th>
                <th><spring:message code="report.place" /></th>
                <th><spring:message code="report.startTime" /></th>
                <th><spring:message code="report.endTime" /></th>
                <th><spring:message code="report.difference" /></th>
                <th><spring:message code="report.price" /></th>
            </tr>
            <c:forEach var="booking" items="${bookings}">
            <tr>
                <td>${booking.getIdUser()}</td>
                <td>${booking.extractMonthAndDay()}</td>
                <td>${booking.getIdChild()}</td>
                <td>${booking.getIdRoom()}</td>
                <td>${booking.extractHourAndMinuteFromStartTime()}</td>
                <td>${booking.extractHourAndMinuteFromEndTime()}</td>
                <td>${booking.getDifference()}</td>
                <td>${booking.getPrice(booking.getDifference())}</td>
            </tr>
            </c:forEach>
        </table>
    </div>