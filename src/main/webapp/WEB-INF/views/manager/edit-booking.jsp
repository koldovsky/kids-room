<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel='stylesheet' href='resources/css/edit-booking.css'>
<link href='resources/css/formForCalendar.css' rel='stylesheet'/>

<c:url value="/j_spring_security_check" var="listChildrenURL" />

<div>
    <div class="container">
        <div>
            <button class="btn btn-primary"
                    data-toggle="modal" data-target="#create-booking">
            <spring:message code="button.add"/>
            </button>
        </div>
    </div>

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
                                <select id="selectUser" onchange="selectUser(value);" class="form-control">
                                    <lable> Please choose kid </lable>
                                   <option value=" " disabled selected hidden>  </option>
                                   <c:forEach items="${users}" var="user">
                                       <option value="${user.children}" >${user.getFullName()}</option>
                                   </c:forEach>
                                </select>
                            </div>
                            <label for="bookingStartDate">Start date</label>
                            <br>
                            <div class="col-xs-6">
                               <input type="text" class="form-control" id="create-date" placeholder="startDate"
                                     readonly>
                            </div>
                            <div class="col-xs-5">
                                    <input id="create-start-time" type="text" class="time form-control" size="6"/>
                                </div>
                            <br>
                            <div class="form-group">
                                <label for="bookingEndDate">End date</label>
                                <br>
                                <div class="col-xs-5">
                                    <input id="create-end-time" type="text" class="time form-control" size="6"/>
                                </div>
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
    <div class="container">
            <div class="vertical-center-row">
                <div align="center">
                    <div id="dialog" hidden>
                        <form id="form">
                            <div class="form-group">
                                <label for="startDate">Event title</label>
                                <input type="text" class="form-control" id="startDate" placeholder="title">
                            </div>
                            <div class="allDay">
                                <label><input type="checkbox" id="checkbox" value=""> All day</label>
                            </div>

                            <div class="form-group">
                                <label for="title">Start date</label>
                                <br>
                                <div class="col-xs-6">
                                    <input type="text" class="form-control" id="title" placeholder="startDate" readonly>
                                </div>
                                <div class="col-xs-5">
                                    <input id="basicExample" type="text" class="time form-control" size="6"/>
                                </div>
                            </div>
                            <br>


                            <div class="form-group">
                                <label for="endDate">End date</label>
                                <br>
                                <div class="col-xs-6">
                                    <input type="text" class="form-control" id="endDate" placeholder="endDate" readonly>
                                </div>
                                <div class="col-xs-5">
                                    <input id="ender" type="text" class="time form-control" size="6"/>
                                </div>
                            </div>


                            <div class="form-group">
                                <label for="Description">Description</label>
                                <textarea type="text" class="form-control" id="description"
                                          placeholder="description"></textarea>
                            </div>
                            <div class="col-xs-6">
                                <button type="button" class="btn btn-success" id="creating">Create</button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div>
        <div class="container">
                <div class="vertical-center-row">
                    <div align="center">
                        <div id="bookingDialog" hidden>
                            <form id="bookingForm">
                                <div class="form-group">
                                    <label for="bookingStartDate">Start date</label>
                                    <br>
                                    <div class="col-xs-6">
                                        <input type="text" class="form-control" id="bookingStartDate" placeholder="startDate"
                                               readonly>
                                    </div>
                                    <div class="col-xs-5">
                                        <input id="bookingStartTimepicker" type="text" class="time form-control" size="6"/>
                                    </div>
                                </div>
                                <br>

                                <div class="form-group">
                                    <label for="bookingEndDate">End date</label>
                                    <br>
                                    <div class="col-xs-6">
                                        <input type="text" class="form-control" id="bookingEndDate" placeholder="endDate"
                                               readonly>
                                    </div>
                                    <div class="col-xs-5">
                                        <input id="bookingEndTimepicker" type="text" class="time form-control" size="6"/>
                                    </div>
                                </div>


                                <table>
                                    <c:forEach items="${kids}" var="kids" varStatus="loop">

                                        <tr>
                                            <label><input type="checkbox" value=""
                                                          id="checkboxKid${kids.id}">${kids.firstName}</label>
                                                ${loop.index} <br>
                                        </tr>

                                    </c:forEach>

                                    <c:forEach items="${kids}" var="kids" varStatus="loop">

                                        <tr>
                                            <label for="child-comment-${kids.id}">Comment for ${kids.id}:</label>

                                            <input type="text" value="" id="child-comment-${kids.id}">
                                        </tr>
                                    </c:forEach>

                                </table>

                                <div class="col-xs-6">
                                    <button type="button" class="btn btn-success" id="booking">Book</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
</div>


<script src="resources/js/edit-booking.js"></script>
<script src="resources/js/changeroom.js"></script>