<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel='stylesheet' href='resources/css/listBookedChildren.css'>
<c:url value="/j_spring_security_check" var="listChildrenURL" />

<script src="resources/js/bookedkids.js"></script>
<script src="resources/js/header-manager.js"></script>

<div class="tableDiv">
	<table class="table">
        <div class="header">
			<th><spring:message code= "booking.childrens"/></th>
			<th><spring:message code= "booking.time"/></th>
			<th><spring:message code= "booking.arrival"/></th>
			<th><spring:message code= "booking.leave"/></th>
        </div>

			<c:forEach var="booking" items="${listBooking}">
				<tr id="${booking.idBook}" class="trbooking">
						<td class="del">
                            <div class="input-group">
                                    <a id="href-kids" href="profile?id=${booking.child.id}">${booking.child.getFullName()}</a>
                            </div>
						</td>
                        <td class="bookingTime">
                            <div class="input-group">
                                    <fmt:formatDate pattern="HH:mm" value="${booking.bookingStartTime}"/> -
                                    <fmt:formatDate pattern="HH:mm" value="${booking.bookingEndTime}"/>
                            </div>
                        </td>
					<form action="" method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<td>
							<div class="input-group">
								<input type="time" id="arrivalTime" class="form-control"/>
								<span class="input-group-btn">
									<input type="button" class="btn btn-raised btn-sm btn-info"
										onclick="setStartTime(${booking.idBook})"
										value=<spring:message code= "booking.setTime"/>
									</input>
								</span>
							</div>
						</td>
					</form>
					<form action="" method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div>
							<td>
								<div class="input-group">
									<input type="time" id="leaveTime" class="form-control"/>
									<span class="input-group-btn">
										<input type="button" class="btn btn-raised btn-sm btn-info"
											onclick="setEndTime(${booking.idBook})"
											value=<spring:message code= "booking.setTime"/>
										</input>
									</span>
								</div>
							</td>
						</div>
					</form>
				</tr>
			</c:forEach>
	</table>
	<div id="cancelModal" class="modal fade">
		<div class="modal-dialog modal-sm">
			<div class="modal-content" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h2><spring:message code= "booking.cancel"/></h2>
				</div>
				<div class="modal-body">
					<p class="cancelName">
						<spring:message code= "booking.confirmCencelQuestion"/>
					</p>
					<div>
						<button id="closeCencel" class="btn btn-raised" data-dismiss="modal">
							<spring:message code= "booking.closeCencel"/>
						</button>
						<button id="cancelButton" class="btn btn-raised btn-info">
							<spring:message code= "booking.confirmCancel"/>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>



