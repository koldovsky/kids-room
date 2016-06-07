<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:url value="/j_spring_security_check" var="listChildrenURL" />




<div class="tableDiv">

    <select id="selectBoxUser" onchange="selectRoomForManager(value);">

        <option value=" "></option>

        <c:forEach items="${listRoom}" var="room">

            <option value="${room.id}">${room.city}: ${room.address}</option>

        </c:forEach>

    </select>
<table class="table">
		<thead>
			<th class="col-sm-4"><spring:message code= "booking.childrens"/></th>
			<th class="col-sm-4"><spring:message code= "booking.time"/></th>
			<th class="col-sm-4">Change booking</th>
		</thead>
		<tbody>
			<c:forEach var="booking" items="${listBooking}">
				<tr id="${booking.idBook}" class="trbooking">
					<div class="col-sm-4">
						<td  class="kidsName">
							<a href="profile?id=${booking.idChild.id}">${booking.idChild.getFullName()}</a>
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
							Change
						</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	<div id="change-booking-modal" class="modal fade">
    		<div class="modal-dialog">
    			<div class="modal-content" >
    				<div class="modal-header">
    					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    					<h2>Change booking</h2>
    				</div>
    				<div class="modal-body">
    					<div>
    					    <form action="", method="POST">
                                <div class="input-group">
                                    Please choose day
									<input type="date" id="data" name="date" class="form-control"/>
								</div>
								<div class="input-group">
									Choose Start time
									<input type="time" id="startTime" class="form-control"/>
								</div>
								<div class="input-group">
								   Choose End time
									<input type="time" id="endTime" class="form-control" placeholder="Choose End time"/>
								</div>
    					    </form>
    					</div>
    					<div>
    						<button id="close-modal" class="btn btn-raised" data-dismiss="modal">
    							Close
    						</button>
    						<button id="change-booking" class="btn btn-raised btn-info">
                                 Cahge
                            </button>

    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
</div>



<script>

    function selectRoomForManager(value){


    }

    function changeBooking(id){
     var idElement="#"+id;
        $('#change-booking-modal').find('#change-booking').click(function(){
            var getData = $(this).closest('.modal-dialog');
            var inputDate = {
                id: id,
                startTime: getData.find('#data').val()+" "+getData.find('#startTime').val(),
                endTime: getData.find('#data').val()+" "+ getData.find('#endTime').val(),
            };
            $.ajax({
                url: 'manager-change-booking',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(inputDate),
                success: function(data){
                    if(!data){
                        alert(data);
                    } else
                    $('#change-booking-modal').modal('hide');
                }
            });
        });

    }

</script>



