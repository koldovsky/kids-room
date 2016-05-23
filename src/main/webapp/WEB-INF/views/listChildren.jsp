<%@ page isELIgnored="false" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript" ></script>
<link rel='stylesheet' href='resources/css/listBookedChildren.css'>
<c:url value="/j_spring_security_check" var="listChildrenURL" />



<div class="col-md-8">

  <div>
      ${BookingPerDay}
      Today is <fmt:formatDate pattern="dd-MM-yyy" value="${nowTime}"/>
  </div>

  <table class="table table-striped table-hover" id="tabble">
      <caption><h2> <spring:message code="kids.list"/></h2></caption>
      <th>Childrens</th>
      <th>Booking time</th>
      <th>Arrival time</th>
      <th>Leave time</th>
      <th>Cancel booking</th>

      <c:forEach var="booking" items="${listBooking}">
        <tr id="${booking.idBook}">

          <td  class="kidsName">
                <a href="profile?id=${booking.idChild.id}">${booking.idChild.getFullName()}</a>
          </td>
                      <td class="bookingTime">
                        <fmt:formatDate pattern="HH:mm" value="${booking.bookingStartTime}"/> -
                        <fmt:formatDate pattern="HH:mm" value="${booking.bookingEndTime}"/>
                     </td>
         <form action="" method="POST">
            <td>
                      <div class="input-group">
                           <input type="time" class="arrivalTime"
                           value=<fmt:formatDate pattern="HH:mm" value="${nowTime}"/>
                           </input>
                           <span class="input-group-btn">
                                <input type="button" class="btn" onclick="setStartTime(${booking.idBook})" value="Ok"/>
                           </span>
                      </div>
         </form>
         <form action="" method="POST">
            <td>

                    <div class="input-group">
                          <input type="time" class="leaveTime"
                          value=<fmt:formatDate pattern="HH:mm" value="${nowTime}"/>
                          </input>
                          <span class="input-group-btn">
                                <input type="button" class="btn" onclick="setEndTime(${booking.idBook})" value="Ok"/>
                          </span>
                    </div>

            </td>
          </form>

          <td>

                <button onclick="cancelBooking(${booking.idBook})">Canceled</button>

          </td>
            <td id = "ss">

                  </td>
            </tr>

      </c:forEach>
  </table>
</div>
<center>

<script src="resources/js/bookedkids.js"></script>


