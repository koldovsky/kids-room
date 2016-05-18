<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Update location</title>
    <link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
</head>

<div class="for-table">
   <form:form modelAttribute="room" action="adm-update-room" method="post">

       <legend class="for-field"><strong>Update room</strong></legend>

       <form:hidden path="id" />

       <div class="form-group">
          <label for="name" class="for-field">Room name
             <form:input path="name" id="name" value="${room.name}" class="form-control" required="required"/>
          </label>
       </div>

       <div class="form-group">
          <label for="address" class="for-field">Room address
             <form:input path="address" id="address" value="${room.address}" class="form-control" required="required"/>
          </label>
       </div>

       <div class="form-group">
          <label for="phoneNumber" class="for-field">Room phone number
             <form:input path="phoneNumber" id="phoneNumber" value="${room.phoneNumber}" class="form-control" required="required"/>
          </label>
       </div>

       <div class="form-group">
          <label for="manager" class="for-field"> Room-manager
             <select name="managers" required class="form-control" id="mySelect">
                <option path="manager" id="manager" value="${room.manager.id}" class="form-control">
                                        ${room.manager.firstName} ${room.manager.lastName}</option>
                <c:forEach var="manager" items="${managerList}" >
                   <option value="${manager.id}">${manager.firstName} ${room.manager.lastName}</option>
                </c:forEach>
             </select>
          </label>
       </div>


       <div class="form-group">
          <label for="capacity" class="for-field">Room capacity
             <form:input path="capacity" id="capacity" value="${room.capacity}" class="form-control" required="required"/>
          </label>
       </div>


       <div class="form-group">
          <label for="city" class="for-field">Room city
             <select path="city" id="city" name="cities" required class="form-control">
                <option path="city" id="city" value="${room.city.idCity}" class="form-control">${room.city.nameCity}</option>
                <c:forEach var="city" items="${cityList}" >
                   <option value="${city.idCity}">${city.nameCity}</option>
                </c:forEach>
             </select>
          </label>
       </div>

       <div class="form-group">
          <button type="submit" name="submit" class="for-button">Submit</button>
          <button type="reset" name="reset" class="for-button">Cancel</button>
       </div>
   </form:form>
</div>