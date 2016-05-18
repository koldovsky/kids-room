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
    <form action="" method='POST'>
    <div class="col-md-4">
        <p ><input id="inputTime" type="date" value="${BookingPerDay}"/></p>
    </div>
    <div class="col-md-4">
        <input type="button" id="bookingStartTime" value="Chouse Day"/>
    </div>
    </form>


   <table class="table table-hover" id="tabble">
      <caption><h2>List of kids</h2></caption>
      <tr>
        <th class="odd">Kids name</th>
    </tr>
    <c:forEach var="booking" items="${listBooking}">
    <tr>
        <td  class="showTable" id="${booking.idBook}" onclick="show(${booking.idBook})">
            <a href="#">${booking.idChild.getFullName()}</a>
        </td>
    </tr>
</c:forEach>
<tr>
   <td>
       <button id="butAddKid"> Add kid</button>
   </td>
</tr>
</table>
<div >
    <table class="col-md-11" id="addKids">
        <tr>
           <td>
               <input type="search"> </input>
           </td>
       </tr>
       <c:forEach var="children" items="${listChild}">

       <tr>
           <td>
             <a href="#" id="1524">${children.getFullName()}</a>
         </td>
     </tr>
 </c:forEach>
</table>
</div>
</div>

<div id="reportTime">
	<input class="buttons2" type="submit" value="Arrival time"/>
	<input class="buttons2" type="submit" value="Departure time"/>
	<table >
        <form action="" method="POST">
            <input id="idBook" name="idBook" type="hidden"/>
            <tr>
                <th class="odd"><spring:message code="booking.time" /></th>
                <th class="odd">Real time</th>
            </tr>
            <tr>
                <td id="startTime"> </td>
                <td>
                    <input id="realTime" name="realTime" type="time"/>
                </td>
            </tr>
            <tr>
                <td  colspan="2">
                    <input type="button" class="buttons" id="ApplyBooking" value="Apply Booking"/>
                </td>
            </tr>
        </form>
    </table>
</div>
<script>

 $('#addKids').hide();
 $('#butAddKid').click(function(){
    $('#addKids').show();
});



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
            document.getElementById("startTime").innerHTML = obj.startTime;
            $('#idBook').val(obj.id);
            $('#realTime').val(obj.reportTime);

        }
    });
}



    $('#ApplyBooking').click(function(){

        var inputData = {
            startTime: $('#realTime').val(),
            id: $('#idBook').val(),
        };
            $.ajax({
                url: "setTime",
                contentType: 'application/json',
                data:   JSON.stringify(inputData),
                type: 'POST',
                success: function(data){
                location.reload();
                },
                error: function(){
                alert(status);
                }
                });
    });




</script>


