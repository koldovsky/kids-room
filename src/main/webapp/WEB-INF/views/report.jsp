<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/j_spring_security_check" var="reportURL" />
<script src="resources/js/report.js"></script>

    <form id="generateAReport" action="allBookings">
        <div id="dateThenDiv">
            <h2>Choose start time</br>
            <input type="date" value="${dateThen}" name="dateThen" id="dateThenInput"></h2>
        </div>
        <div id="dateNowDiv">
            <h2>Choose end time</br>
            <input type="date" value="${dateNow}" name="dateNow" id="dateNowInput"></h2>
        </div>
    </form>

    <div class="table">
        <table>
            <caption>
                <h2>
                    <spring:message code="report.activeParents" /></br>
                    <span class="smallText">(${dateThen} - ${dateNow})</span>
                </h2>
            </caption>

            <tr>
                <th><spring:message code="report.name" /></th>
                <th><spring:message code="report.surname" /></th>
                <th><spring:message code="report.email" /></th>
                <th><spring:message code="report.phone" /></th>
                <th><spring:message code="report.bookings" /></th>
            </tr>

            <c:forEach var="booking" items="${bookings}">
            <tr>
                <td>${booking.getIdUser().getFirstName()}</td>
                <td>${booking.getIdUser().getLastName()}</td>
                <td>${booking.getIdUser().getEmail()}</td>
                <td>${booking.getIdUser().getPhoneNumber()}</td>
                <td id="${booking.getIdUser().getEmail()}" class="parentRow">
                    <a><spring:message code="report.details" /></a>
                </td>
            </tr>
            </c:forEach>

        </table>
    </div>

    <input type="submit" form="generateAReport" value=<spring:message code="report.generate" /> class="btn-primary">

    <form action="allBookingsPerParent" id="allBookingsPerParentForm">
        <input type="hidden" id="parentEmailField" name="parentEmail"/>
        <input type="hidden" id="dateThenField" name="dateThen"/>
        <input type="hidden" id="dateNowField" name="dateNow"/>
    </form>