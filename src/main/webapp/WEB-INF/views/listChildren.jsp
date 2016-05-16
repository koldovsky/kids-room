<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript" ></script>
<link rel='stylesheet' href='resources/css/listBookedChildren.css'>
<c:url value="/j_spring_security_check" var="listChildrenURL" />

<script src="resources/js/bookedkids.js"></script>




<div class="col-md-4">

<div class="col-md-4">
    <p ><input name="bookingStartTime" id="inputTime" type="date" value="${BookingPerDay}"/></p>
</div>
<div>
 <input type="submit" id="butChouseTime" onclick="choseBookingDay(${BookingPerDay})" value="Chouse date"></input>
 </div>

	<table class="col-md-11" id="tabble">
		<caption><h2>List of kids</h2></caption>
            <tr>
                <th class="odd">Kids name</th>
            </tr>
		<c:forEach var="booking" items="${listBooking}">
            <tr>
                <td  class="showTable" id="${booking.idBook}" onclick="show(${booking.idBook})">
                <c:out value="${booking.idChild.getFullName()}"/>
                </td>
            </tr>
	    </c:forEach>

</table>
</div>

<div id="reportTime">
	<input class="buttons2" type="submit" value="Arrival time"/>
	<input class="buttons2" type="submit" value="Departure time"/>
	<table >


    <form:form action="result" modelAttribute="bookedJSP" method="POST">


                <form:input path="idBook" id="elogin" type="hidden"/>


            <tr>
                <th class="odd"><spring:message code="booking.time" /></th>
                <th class="odd">Real time</th>
            </tr>

            <tr>
                <td id="startTime"> </td>
                <td>
                <input name="bookingTime"  id="realTime" type="time"/>

                </td>
            </tr>

            <tr>

                <td  colspan="2">
                    <form:button class="buttons"> Apply Booking </form:button>
                </td>
            </tr>
            </form:form>

</table>

</div>
<script>

   $('#reportTime').hide();

    $('.showTable').click(function(){

        $('#reportTime').show();
    });

    function show(a){

        var str = "getCompan/"+a;
        $.ajax({
            url: str,
            success: function(result){
            var text = result;
            var obj = JSON.parse(text);
            document.getElementById("startTime").innerHTML = obj.start;
            $('#elogin').val(obj.id);
            $('#realTime').val(obj.start);

        }
        });
    }



</script>




