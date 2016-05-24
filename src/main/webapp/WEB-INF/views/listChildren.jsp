<%@ page isELIgnored="false" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript" ></script>
<link rel='stylesheet' href='resources/css/listBookedChildren.css'>
<c:url value="/j_spring_security_check" var="listChildrenURL" />





<div class="tableDiv">
  <table class="table">
      <caption><h2> <spring:message code="kids.list"/></h2></caption>

      <th class="col-sm-6"><spring:message code= "booking.childrens"/></th>
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
            <td class='arr'>
                      <div class="input-group">
                           <input type="time" id="arrivalTime" class="form-control"/>
                           <span class="input-group-btn">
                                <input type="button" class="btn" onclick="setStartTime(${booking.idBook})" value="Ok"/>
                           </span>
                      </div>
         </form>
         <form action="" method="POST">
            <td>
                    <div class="input-group">
                          <input type="time" id="leaveTime" class="form-control"/>
                          <span class="input-group-btn">
                                <input type="button" class="btn" onclick="setEndTime(${booking.idBook})" value="Set"/>
                          </span>
                    </div>
            </td>

          </form>
          <td>
                <button class="btn" onclick="cancelBooking(${booking.idBook})">
                    <spring:message code= "booking.canceled"/>
                </button>
          </td>
      </c:forEach>
  </table>
</div>


<script src="resources/js/bookedkids.js"></script>



