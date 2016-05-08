<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/j_spring_security_check" var="parentBookingsURL" />

<c:forEach var="booking" items="${bookings}">
    <div id="${booking.getIdBook()}"">
        <input type="text" value="${booking.getIdChild()}" disabled>
        <input type="text" value="${booking.getBookingStartTime()}" disabled>
        <input type="text" value="${booking.getBookingEndTime()}" disabled>
    </div>
</c:forEach>