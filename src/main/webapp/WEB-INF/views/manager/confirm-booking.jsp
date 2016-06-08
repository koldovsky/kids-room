<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel='stylesheet' href='resources/css/listBookedChildren.css'>
<c:url value="/j_spring_security_check" var="listChildrenURL" />


<div class="tableDiv">
	<div ng-app="allKidsList">
		<div ng-controller="addBookingController">
			<div class="input-group">
				<input type="text" class="form-control -raised" placeholder="Search..." ng-model="searchField"
				ng-change="searchChildren(searchField)">
			</div>
			<div class="col-sm-6">
				<div ng-repeat="child in children">
					<div>
						{{child.fullName}}
						{{child.id}}
					</div>
				</div>
			</div>
		</div>
	</div>
	<table class="table">
		<thead>
			<th class="col-sm-2"><spring:message code= "booking.childrens"/></th>
			<th class="col-sm-2"><spring:message code= "booking.time"/></th>
			<th class="col-sm-2"><spring:message code= "booking.arrival"/></th>
			<th class="col-sm-2"><spring:message code= "booking.leave"/></th>
			<th class="col-sm-2"><spring:message code= "booking.cancel"/></th>
		</thead>
		<tbody>
			<c:forEach var="booking" items="${listBooking}">
				<tr id="${booking.idBook}" class="trbooking">
					<div class="col-sm-4">
						<td>
							<a id="href-kids" href="profile?id=${booking.idChild.id}">${booking.idChild.getFullName()}</a>
						</td>
					</div>
					<td class="bookingTime" class="col-sm-4">
						<fmt:formatDate pattern="HH:mm" value="${booking.bookingStartTime}"/> -
						<fmt:formatDate pattern="HH:mm" value="${booking.bookingEndTime}"/>
					</td>
					<form action="" method="POST">
						<td class="col-sm-2">
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
						<div class="col-sm-4">
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
					<td class="cancelClass" class="col-sm-4">
						<button class="btn btn-sm btn-warning"
							data-toggle="modal" data-target="#cancelModal"
							onclick="cancelBooking(${booking.idBook})">
							<spring:message code= "booking.canceled"/>
						</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="cancelModal" class="modal fade">
		<div class="modal-dialog">
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
	<div id="invalidTimeModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<p>You enter leave time less then arrival time</p>
				</div>
				<div class="modal-body">
					<p>Please enter a correct leaving time </p>
					<div>
						<button id="closeCencel" class="btn btn-raised" data-dismiss="modal">
							Ok
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="resources/js/bookedkids.js"></script>
<script src="resources/js/allkidslist_table_controller.js"></script>
<script src="resources/js/addbooking_controller.js"></script>
<script src="resources/js/allkidslist_table_service.js"></script>
<script src="resources/js/allkidslist_table_directive.js"></script>
<script src="resources/js/allkidslist_app.js"></script>



