<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel='stylesheet' href='resources/css/edit-booking.css'>
<link href='resources/css/formForCalendar.css' rel='stylesheet'/>
<c:url value="/j_spring_security_check" var="listChildrenURL" />

<script src='resources/js/renderCalendar.js'></script>
<script src='resources/js/moment.min.js'></script>
<script src='resources/js/jquery.min.js'></script>
<script type='text/javascript' src='resources/js/uk.js'></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link href='resources/css/formForCalendar.css' rel='stylesheet'/>
<script type="text/javascript" src="resources/js/jquery.timepicker.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/jquery.timepicker.css"/>


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
                                 <input type="date" id="data" class="form-control" required/>
                            </div>
                            <div class="input-group">
                                <label><spring:message code="booking.createStartTime"/></label>
                                <input id="bookingUpdatingStartTimepicker" type="text" class="time form-control" required/>
                            <br>
                            <div>
                                <label><spring:message code="booking.createEndTime"/></label>
                                <input id="bookingUpdatingEndTimepicker" type="text" class="time form-control" required />
                            </div>
                        </div>
                        <div class="col-xs-6">
                            <button type="button" class="btn btn-success" id="updatingBooking">Update</button>
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
                    <form id="bookingForm">
                        <div class="form-group">
                            <label for="selectUser">Choose parent</label>
                             <select id="selectUser" onchange="selectUser();" class="form-control">
                                 <lable> Please choose kid </lable>
                                 <option value=" " disabled selected hidden>  </option>
                                 <c:forEach items="${users}" var="user">
                                    <option value="${user.id}" >${user.getFullName()}</option>
                                 </c:forEach>
                            </select>
                            <label for="bookingStartDate">Choose date</label>
                            <br>
                            <div>
                                <input type="date" class="form-control" id="bookingStartDate" placeholder="booking date"/>
                            </div>
                            <input id="kostil" hidden/>
                            <div class="col-xs-5">
                                <input id="bookingStartTimepicker" type="text" class="time form-control" size="6"
                                placeholder="start time"/>
                            </div>
                            <div class="col-xs-5">
                                <input id="bookingEndTimepicker" type="text" class="time form-control" size="6"
                                placeholder="end time"/>
                            </div>
                        </div>
                        <br>
                         <div id="kids">
                         </div>

                        <div class="col-xs-6">
                            <button type="button" class="btn btn-success" id="booking">Book</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>










<script src="resources/js/edit-booking.js"></script>
<script src="resources/js/header-manager.js"></script>