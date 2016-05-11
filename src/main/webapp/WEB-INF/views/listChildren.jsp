<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel='stylesheet' href='resources/css/list-of-booked-kids.css'>
<c:url value="/j_spring_security_check" var="listChildrenURL" />

<script src="resources/js/bookedkids.js"></script>

<div class="col-md-4">
	<table class="col-md-11">
		<caption><h2>List of kids</h2></caption>
		<tr>
			<th class="odd">Kids name</th>
		</tr>
		<c:forEach var="kids" items="${listChildren}">
		<tr>

			<td class="hideTable"><c:out value="${kids.getFirstName()} ${kids.getLastName()}" /></td>
		</tr>
	</c:forEach>
</table>
</div>

<div id="reportTime">
	<input class="buttons2" type="submit" value="Arrival time"/>
	<table >
		<form:form action="result" modelAttribute="bookedJSP" method="POST">

		<tr>
			<th class="odd">Booking time</th>
			<th class="odd">Real time</th>
		</tr>

		<c:forEach var="booked" items="${listBooking}">
		<tr>

			<td><c:out value="${booked.getBookingStartTime()}"/></td>
			<td> <form:input path="bookingStartTime" /></td>
		</tr>
	</c:forEach>
	<tr>
		<td  colspan="2">

			<form:button class="buttons"> Apply Booking </form:button>

		</td>
	</tr>
</form:form>
</table>

</div>


