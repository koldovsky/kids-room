<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/dataTables.bootstrap4.min.css" />
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap4.min.js"></script>

<link rel="stylesheet" type="text/css" href="resources/css/jquery.timepicker.css"/>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script type="text/javascript" src="resources/js/jquery.timepicker.js"></script>
<link rel='stylesheet' href='resources/css/edit-booking.css'>
<link href='resources/css/flow-form.css' rel='stylesheet'/>

<div class="container">
    <div class="table-edit">
        <div id="choose-time-inp">
            <form action="" , method="POST">
                <input type="hidden" name="${csrf.parameterName}" value="${_csrf.token}"/>
                <input id="date-booking" class="form-control" type="date"/>
            </form>
        </div>
        <div id="create-booking-btn">
            <button class="btn btn-primary" onclick="openCreateBookingDialog()">
                Add a kid
            </button>
        </div>
        <div id="nav-group">
            <nav>
                <ul class="nav nav-pills">
                    <button id="btn-all" class="btn btn-raised" onclick="allBooking()">All</button>
                    <button id="btn-booked" class="btn btn-raised" onclick="bookedBooking()">Booked</button>
                    <button id="btn-active" class="btn btn-raised" onclick="activeBooking()">Arrived</button>
                    <button id="btn-leaved" class="btn btn-raised" onclick="leavedBooking()">Left</button>
                </ul>
            </nav>
        </div>
        <br>

        <table id="booking-table" class="col-md-12" >
            <thead >
            <tr>

                <th class="col-xs-1">#</th>
                <th class="col-xs-3"><spring:message code="booking.childrens"/></th>
                <th><spring:message code="booking.time"/></th>
                <th><spring:message code="booking.arrival"/></th>
                <th><spring:message code="booking.leave"/></th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>
</div>

<div class="container">
    <div class="vertical-center-row">
        <div align="center">
            <div id="bookingUpdatingDialog" hidden>
                <form id="bookingUpdatingForm">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <div class="input-group">
                            <label><spring:message code="booking.createDate"/></label>
                            <input type="date" id="data-edit" name="date" class="form-control"/>
                        </div>
                        <div class="input-group">
                            <label><spring:message code="booking.createStartTime"/></label>
                            <input id="bookingUpdatingStartTimepicker" type="text" name="start"
                                   class="time form-control picker"/>
                            <br>
                            <div>
                                <label><spring:message code="booking.createEndTime"/></label>
                                <input id="bookingUpdatingEndTimepicker" type="text" name="end"
                                       class="time form-control picker"/>
                            </div>
                            <div>

                                <textarea type="text" class="form-control" id="kid-comment"
                                          placeholder="Some comment"></textarea>
                            </div>
                        </div>

                        <input type="button" class="btn btn-success pull-left" id="updatingBooking" value="Update"></input>


                        <button type="button" class="btn btn-danger pull-right" id="deletingBooking">Delete</button>

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
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <label for="selectUser">Choose parent</label>
                        <select id="selectUser" name="select" onchange="selectUser();" class="form-control">
                            <lable> Please choose kid</lable>
                            <option value=" " disabled selected hidden></option>
                            <c:forEach items="${users}" var="user">
                                <option value="${user.id}">${user.getFullName()}</option>
                            </c:forEach>
                        </select>
                        <label for="bookingStartDate">Booking date</label>
                        <br>
                        <div>
                            <input type="text" class="form-control" id="bookingStartDate" placeholder="booking date"
                                   readonly/>
                        </div>
                        <input id="kids-count" hidden/>
                        <div>
                            <input id="bookingStartTimepicker" type="text" name="started" class="form-control picker"
                                   placeholder="start time"/>
                        </div>
                        <br>
                        <div>
                            <input id="bookingEndTimepicker" type="text" name="ended" class="form-control picker"
                                   placeholder="end time"/>
                            <br>
                        </div>
                        <div id="kids">
                        </div>
                        <br>
                        <input type="button" class="btn btn-success" id="booking" value="Book"></input>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div id="invalidTimeModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div align="center">
                    <br>
                    <h3>Incorrect arrive or leave time</h3>
                    <h4>Please enter correct time</h4>
                    <button type="button" class="btn btn-success" data-dismiss="modal">Close</button>

                </div>
            </div>
        </div>
    </div>
</div>
<div id="updatingSuccess" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-body">
                <div align="center">
                    <br>
                    <h4>Booking updated</h4>
                    <button type="button" class="btn btn-success" data-dismiss="modal">Close</button>
                </div>

            </div>
        </div>
    </div>
</div>
<div id="updatingInvalid" class="modal fadde">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <p>We regret to inform you that there are no available places left in the room.</p>
            </div>
            <div class="modal-body">
                <button type="button" class="btn btn-success" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div id="creatingfailue1" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-body-err">
                <div align="center">
                    <br>
                    <h4>Please try to select another time, because currently this room is full!</h4>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>

            </div>
        </div>
    </div>
</div>
<div id="creatingfailue2" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-body-err">
                <div align="center">
                    <br>
                    <h4>Error, duplicate booking!</h4>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>

            </div>
        </div>
    </div>
</div>
<div id="createSuccess" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div align="center">
                    <br>
                    <div class="checkmark">
                        <svg version="1.1" id="Layer_1" x="0px" y="0px"
                             viewBox="0 0 161.2 161.2" enable-background="new 0 0 161.2 161.2" xml:space="preserve">
<circle class="path" fill="none" stroke="#FFFFFF" stroke-width="4" stroke-miterlimit="10" cx="80.6" cy="80.6" r="62.1"/>
                            <polyline class="path" fill="none" stroke="#FFFFFF" stroke-width="6" stroke-linecap="round"
                                      stroke-miterlimit="10" points="113,52.8
	74.1,108.4 48.2,86.4 "/>
</svg>
                    </div>
                    <h2>New Booking Created</h2>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="cancelModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-body text-center">
                <br>
                <h2><spring:message code="booking.cancel"/></h2>
                <p class="cancelName ">
                    <spring:message code="booking.confirmCancelQuestion1"/>
                    <br>
                    <spring:message code="booking.confirmCancelQuestion2"/>
                </p>
                <button id="cancelButton" class="btn  btn-success    ">
                    <spring:message code="booking.confirmCancel"/>
                </button>
                <button id="closeCencel" class="btn btn-danger  " data-dismiss="modal">
                    <spring:message code="booking.closeCencel"/>
                </button>



            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="resources/js/edit-booking.js"></script>
<script src="resources/js/header-manager.js"></script>


