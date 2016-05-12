<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="reportUrl" />
<script src="resources/js/report.js"></script>

    <div class="table">
        <table>
            <caption>
            <h2>List of active parents <span class="smallText">(${dateThen} - ${dateNow})</span></h2>
            </caption>
            <tr>
                <th>Name</th>
                <th>Surname</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Bookings</th>
            </tr>
            <c:forEach var="booking" items="${bookings}">
            <tr>
                <td>${booking.getIdUser().getFirstName()}</td>
                <td>${booking.getIdUser().getLastName()}</td>
                <td>${booking.getIdUser().getEmail()}</td>
                <td>${booking.getIdUser().getPhoneNumber()}</td>
                <td id="${booking.getIdUser().getEmail()}" class="parentRow"><a>See details</a></td>
            </tr>
            </c:forEach>
        </table>
    </div>

    <form action="allParentsBookings">
        <label><h2>Choose start time: <input type="date" value="${dateThen}" name="dateThen"></label></h2>
        <label><h2>Choose end .time: <input type="date" value="${dateNow}" name="dateNow"></label></h2>
        <input class="btn-primary" type="submit" value="Generate">
    </form>

    <form action="parentbookings" id="parentBookings">
        <input type="hidden" id="hiddenField" name="parentEmail"/>
    </form>