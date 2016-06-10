<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel='stylesheet' href='resources/css/edit-booking.css'>

<c:url value="/j_spring_security_check" var="listChildrenURL" />

<div>

<div class="container">
    <div>
        <button class="btn btn-primary"
                data-toggle="modal" data-target="#create-booking">
        <spring:message code="button.add"/>
        </button>
    </div>

    <select id="selectRoom" onchange="selectRoomForManager(value);">
        <option value="sadfgd">sadfgd</option>
        <option value="sadsdfsdfgsdgfgd">sadsdfsdfgsdgfgd</option>
        <c:forEach items="${listRoom}" var="room">
            <option value="${room.id}">${room.city}: ${room.address}</option>
        </c:forEach>

    </select>



<table class="table-edit">
        <div id="set-time">
            <div class="input-group" id="chose-data">
                <form action="", method="POST">
                    <h3><spring:message code="booking.createDate"/> </h3>
                    <input id="data-booking" name="date" class ="form-control" type = "date"
                    value='<fmt:formatDate pattern="yyyy-MM-dd" value="${today}"/>'/>
                </form>
            </div>
        </div>
		<div id ="body-booking">
			<th><spring:message code= "booking.childrens"/></th>
			<th><spring:message code= "booking.time"/></th>
			<th><spring:message code= "button.edit"/></th>
			<th><spring:message code= "booking.cancel"/></th>
			<c:forEach var="booking" items="${listBooking}">
				<tr id="${booking.idBook}" class="trbooking">
					<div class="col-sm-4">
						<td  class="kidsName">
							<a href="profile?id=${booking.idChild.id}">${booking.idChild.getFullName()}</a>
						</td>
					</div>
					<td class="bookingTime" class="col-sm-4">
						<fmt:formatDate pattern="HH:mm" value="${booking.bookingStartTime}"/> -
						<fmt:formatDate pattern="HH:mm" value="${booking.bookingEndTime}"/>
					</td>
					<td class="change-booking" class="col-sm-4">
						<button class="btn btn-sm btn-primary"
							data-toggle="modal" data-target="#change-booking-modal"
							onclick="changeBooking(${booking.idBook})">
                            <spring:message code= "button.edit"/>
						</button>
					</td>
                    <td class="cancelClass" class="col-sm-4">
						<button class="btn btn-sm btn-warning"
							data-toggle="modal" data-target="#cancelModal"
							onclick="cancelBooking(${booking.idBook})">
							<spring:message code= "booking.canceled"/>
						</button>
					</td>
				</tr>
			</c:forEach>
		</div>
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

	<div id="change-booking-modal" class="modal fade ">
    		<div class="modal-dialog modal-sm">
    			<div class="modal-content" >
    				<div class="modal-header">
    					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    					<h3> <spring:message code = "booking.edit"/> </h3>
    					<hr/>
    				</div>
    				<div class="modal-body">
    					<div>
    					    <form action="", method="POST">
                                <div class="input-group">
                                   <label><spring:message code="booking.createDate"/></label>
									<input type="date" id="data" name="date" class="form-control"/>
								</div>
								<div class="input-group">
								<label><spring:message code="booking.createStartTime"/></label>
									<input type="time" id="startTime" class="form-control"/>
								</div>
								<div class="input-group">
								   <label><spring:message code="booking.createEndTime"/></label>
									<input type="time" id="endTime" class="form-control"/>
								</div>
    					    </form>
    					 <span class="validate" id="wrong-interval"> <spring:message code="booking.anothertime"/> </span>
    					 <span class="validate" id="fill-in"> Please fill in all fields </span>
    					</div>
    					<div>
    						<button id="close-modal" class="btn btn-raised" data-dismiss="modal">
    							<spring:message code= "user.close"/>
    						</button>
    						<button id="change-booking" class="btn btn-raised btn-info">
                                 <spring:message code= "button.edit"/>
                            </button>
    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
    	<div id="create-booking" class="modal fade" tabindex="-5">
    	    <div class="modal-dialog modal-sm">
    	        <div class="modal-content">
    	            <div class="modal-header">
    	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    	               <h3> <spring:message code="booking.addreservation"/></h3>
    	               <hr/>
    	            </div>
                    <div class="modal-body">
                        <div>
                            <select id="selectChild" onchange="selectSelectKid(value);" class="form-control">
                                <lable> Please choose kid </lable>
                               <option value=" " disabled selected hidden>  </option>
                               <c:forEach items="${listChild}" var="child">
                                   <option value="${child.id}" >${child.getFullName()}</option>
                               </c:forEach>
                            </select>
                        </div>
                        <div>
                            <input id="create-date" type="date" class="form-control"/>
                        </div>
                        <div>
                            <input id="create-start-time" type="time" class="form-control"/>
                        </div>
                        <div>
                            <input id="create-end-time" type="time" class="form-control"/>
                        </div>
                        <div>
                            <textarea id ="create-comment" class="form-control"></textarea>
                        </div>
                        <button id ="send-button" class="btn btn-info">
                            <spring:message code="booking.create"/>
                        </button>
                    </div>
                </div>
    	    </div>
    	</div>
</div>
</div>

<script src="resources/js/bookedkids.js"></script>
<script>


</script>





