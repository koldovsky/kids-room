<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel='stylesheet' href='resources/css/edit-booking.css'>
<link rel="stylesheet" href="http://jqueryvalidation.org/files/demo/site-demos.css">
<link rel="stylesheet" type="text/css" href="resources/css/jquery.timepicker.css"/>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link href='resources/css/formForCalendar.css' rel='stylesheet'/>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script type="text/javascript" src="resources/js/jquery.timepicker.js"></script>
<script src="resources/js/header-manager.js"></script>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<script src="resources/js/validator.js"></script>

<div class="container">
             <div id="choose-time-inp">
                 <form action="", method="POST">
                    <input id="data-booking" class ="form-control" type = "date"/>
                  </form>
             </div>
            <div id="create-booking-btn">
                 <button class="btn btn-primary" onclick="createBooking()">
                    New booking
                 </button>
            </div>
            <div id="nav-group">
                <nav>
                      <button class="btn btn-raised" onclick="allBooking()">All</button>
                      <button class="btn btn-raised" onclick="bookedBooking()">Booked</button>
                      <button class="btn btn-raised" onclick="activeBooking()">Arrived</button>
                      <button class="btn btn-raised" onclick="leavedBooking()">Left</button>
                </nav>
            </div>

    <div class = "tableDiv">
        <table class="table-edit">
                <div id ="body-booking">
                    <thead>
                        <th class="col-xs-1"> № </th>
                        <th class="col-xs-3"><spring:message code= "booking.childrens"/></th>
                        <th class="col-xs-2"><spring:message code= "booking.time"/></th>
                        <th class="col-xs-2"><spring:message code= "booking.arrival"/></th>
                        <th class="col-xs-2"><spring:message code= "booking.leave"/></th>
                    </thead>
                </div>
        </table>
    </div>
</div>


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
</div>


 <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="bookingUpdatingDialog" hidden>
                    <form id="bookingUpdatingForm">
                        <div class="form-group">
                            <div class="input-group">
                                 <label><spring:message code="booking.createDate"/></label>
                                 <input type="date" id="data-edit" name="date" class="form-control"/>
                            </div>
                            <div class="input-group">
                                <label><spring:message code="booking.createStartTime"/></label>
                                <input id="bookingUpdatingStartTimepicker" type="text" name="start" class="time form-control" />
                            <br>
                            <div>
                                <label><spring:message code="booking.createEndTime"/></label>
                                <input id="bookingUpdatingEndTimepicker" type="text" name="end" class="time form-control" />
                            </div>
                        </div>
                        <div class="col-xs-6">
                            <input type="submit" class="btn btn-success" id="updatingBooking" value="Update"></input>
                        </div>
                        <div class="col-xs-6">
                            <button type="button" class="btn btn-danger" id="deletingBooking">Delete</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
 </div>

 <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="bookingDialog" hidden>
                    <form id="bookings">
                        <div class="form-group">
                            <label for="selectUser">Choose parent</label>
                             <select id="selectUser" name="select" onchange="selectUser();" class="form-control">
                                 <lable> Please choose kid </lable>
                                 <option value=" " disabled selected hidden>  </option>
                                 <c:forEach items="${users}" var="user">
                                    <option value="${user.id}" >${user.getFullName()}</option>
                                 </c:forEach>
                            </select>
                            <label for="bookingStartDate">Booking date</label>
                            <br>
                            <div>
                                <input type="text" class="form-control" id="bookingStartDate" placeholder="booking date" readonly/>
                            </div>
                            <input id="kostil" hidden/>
                            <div>
                                <input id="bookingStartTimepicker" type="text" name="started" class="form-control"
                                placeholder="start time"/>
                            </div>
                            <br>
                            <div>
                                <input id="bookingEndTimepicker" type="text" name="ended" class="form-control"
                                placeholder="end time"/>
                            </div>
                             <div id="kids">
                             </div>
                             <br>
                             <input type="submit" class="btn btn-success" id="booking" value="Book"></input>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


	<div id="invalidTimeModal" class="modal">
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








<script src="resources/js/edit-booking.js"></script>