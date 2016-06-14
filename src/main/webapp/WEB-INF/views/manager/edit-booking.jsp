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




















<div class="choose-time">
     <div>
        <button class="btn btn-primary" onclick="createBooking()">
            <spring:message code="button.add"/>
        </button>
     </div>
 </div>
 <div class="choose-time">
                <div class="input-group" id="chose-data">
                    <form action="", method="POST">
                        <h3><spring:message code="booking.createDate"/></h3>
                        <input id="data-booking" name="date" class ="form-control" type = "date"/>
                    </form>
                </div>

</div>
<div class="container">
    <table class="table-edit">

            <div id ="body-booking">
                <th class="col-xs-3"><spring:message code= "booking.childrens"/></th>
                <th class="col-xs-3"><spring:message code= "booking.time"/></th>
                <th class="col-xs-3"><spring:message code= "button.edit"/></th>
                <th class="col-xs-3"><spring:message code= "button.edit"/></th>
            </div>
                <c:forEach var="booking" items="${listBooking}">
                    <tr id="${booking.idBook}" class="trbooking">
                        <div class="col-sm-4">
                            <td  class="kidsName">
                                <a href="profile?id=${booking.child.id}">${booking.child.getFullName()}</a>
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