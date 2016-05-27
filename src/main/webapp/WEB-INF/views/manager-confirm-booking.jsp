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
                    <table class="table table-hover ">
                        <tr>
                            <td>
                                {{child.fullName}}
                            </td>
                            <td>
                                <button class="btn btn-raised btn-primary">
                                      <span class="glyphicon glyphicon-plus"></span>
                                </button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
  </div>
  <table class="table">
      <th class="col-sm-4"><spring:message code= "booking.childrens"/></th>
      <th class="col-sm-4"><spring:message code= "booking.time"/></th>
      <th class="col-sm-4"><spring:message code= "booking.arrival"/></th>
      <th class="col-sm-4"><spring:message code= "booking.leave"/></th>
      <th class="col-sm-4"><spring:message code= "booking.cancel"/></th>

      <c:forEach var="booking" items="${listBooking}">
         <tr id="${booking.idBook}">
              <td  class="kidsName" >
                    <a href="profile?id=${booking.idChild.id}">${booking.idChild.getFullName()}</a>
              </td>
              <td class="bookingTime">
                    <fmt:formatDate pattern="HH:mm" value="${booking.bookingStartTime}"/> -
                    <fmt:formatDate pattern="HH:mm" value="${booking.bookingEndTime}"/>
              </td>
              <form action="" method="POST">
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
              </form>
              <td class="cancelClass">
                    <button class="btn btn-sm btn-warning"
                                            data-toggle="modal" data-target="#cancelModal">
                        <spring:message code= "booking.canceled"/>
                    </button>
              </td>
        </tr>
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
       </c:forEach>
  </table>
</div>

<script src="resources/js/bookedkids.js"></script>
<script src="resources/js/allkidslist_table_controller.js"></script>
<script src="resources/js/addbooking_controller.js"></script>
<script src="resources/js/allkidslist_table_service.js"></script>
<script src="resources/js/allkidslist_table_directive.js"></script>
<script src="resources/js/allkidslist_app.js"></script>


