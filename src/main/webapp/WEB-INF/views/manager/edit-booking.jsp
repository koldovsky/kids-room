<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel='stylesheet' href='resources/css/edit-booking.css'>

<c:url value="/j_spring_security_check" var="listChildrenURL" />


<div class="container">

    <select id="selectBoxUser" onchange="selectRoomForManager(value);">

        <option value=" "></option>

        <c:forEach items="${listRoom}" var="room">

            <option value="${room.id}">${room.city}: ${room.address}</option>

        </c:forEach>

    </select>

<table class="table-edit">
        <div id="set-time">
            <div class="input-group" id="chose-data">
                <form action="", method="POST">
                    <h3> Choose booking day </h3>
                    <input id="data-booking" name="date" class ="form-control" type = "date"
                    value='<fmt:formatDate pattern="yyyy-MM-dd" value="${today}"/>'/>
                </form>
            </div>
        </div>
		<div id = "body-booking">
			<th><spring:message code= "booking.childrens"/></th>
			<th><spring:message code= "booking.time"/></th>
			<th><spring:message code= "button.edit"/></th>
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
				</tr>
			</c:forEach>
		</div>
	</table>


	<div id="change-booking-modal" class="modal fade">
    		<div class="modal-dialog">
    			<div class="modal-content" >
    				<div class="modal-header">
    					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    					<h2>Change booking</h2>
    				</div>
    				<div class="modal-body">
    					<div>
    					    <form action="", method="POST">
                                <div class="input-group">
                                    Please choose day
									<input type="date" id="data" name="date" class="form-control"/>
								</div>
								<div class="input-group">
									Choose Start time
									<input type="time" id="startTime" class="form-control"/>
								</div>
								<div class="input-group">
								   Choose End time
									<input type="time" id="endTime" class="form-control" placeholder="Choose End time"/>
								</div>
    					    </form>
    					 <span class="validate" id="wrong-interval"> Please choose another time </span>
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
</div>

<script src="resources/js/bookedkids.js"></script>




