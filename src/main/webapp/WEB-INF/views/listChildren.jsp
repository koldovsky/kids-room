<%@ page isELIgnored="false" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript" ></script>
<link rel='stylesheet' href='resources/css/listBookedChildren.css'>
<c:url value="/j_spring_security_check" var="listChildrenURL" />


<div id="same">
<div class="col-md-8">

  <div>

      Today <fmt:formatDate pattern="dd-MM-yyy" value="${nowTime}"/>
  </div>

  <table class="table table-striped table-hover" id="tabble">
      <caption><h2> <spring:message code="kids.list"/></h2></caption>
      <th><spring:message code= "booking.childrens"/></th>
      <th><spring:message code= "booking.time"/></th>
      <th><spring:message code= "booking.arrival"/></th>
      <th><spring:message code= "booking.leave"/></th>
      <th><spring:message code= "booking.cancel"/></th>

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
                           <input type="time" class="arrivalTime"

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

                          </input>
                          <span class="input-group-btn">
                                <input type="button" class="btn" onclick="setEndTime(${booking.idBook})" value="Ok"/>
                          </span>
                    </div>
            </td>
          </form>

          <td>
                <button onclick="cancelBooking(${booking.idBook})">
                    <spring:message code= "booking.canceled"/>
                </button>
          </td>
      </c:forEach>
  </table>
</div>
</div>

<script src="resources/js/bookedkids.js"></script>



