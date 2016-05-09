<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="parentBookingsURL" />

    <div class="table">
        <table>
            <caption><h2>Bookings of ${parent}</h2></caption>
            <tr>
                <th>Kid&#39;s name</th>
                <th>Booking began</th>
                <th>Booking ended</th>
            </tr>
            <c:forEach var="booking" items="${bookings}">
            <tr>
                <td>${booking.getIdChild()}</td>
                <td>${booking.getBookingStartTime()}</td>
                <td>${booking.getBookingEndTime()}</td>
            </tr>
            </c:forEach>
        </table>
    </div>