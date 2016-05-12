<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Edit location</title>
	<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
</head>

<body>
    <div class="rightsidebar" >
        <form action="adm-add-location" method="post" modelAttribute="city">
            <fieldset >
                <legend><strong>General</strong></legend>
                <label>
                    Room name
                    <input type="text" name="name" required ></input>
                </label>
                <label>
                    Address
                    <input type="text" name="address" required ></input>
                </label>
                <label>
                    City
                    <select name="locations" required>
                        <option value="none">none</option>
                         <option value="Lviv">Lviv</option>
                         <option value="Kyiv">Kyiv</option>
                         <option value="Rivne">Rivne</option>
                    </select>
                <label>
                    Room capacity
                    <input type="number" name="capacity" required ></input>
                </label>
                <label>
                    Room phone number
                    <input type="number" name="phoneNumber" required ></input>
                </label>
                <form action="adm-add-location" method="get" >
                <label>
                    Chose room-manager
                    <select name="managers" required>
                        <c:forEach var="manager" items="${managerList}" >
                            <option value="${manager}">${manager}</option>
                        </c:forEach>
                    </select>
                </label>
                </form>

                <button type="submit" name="submit">Submit</button>
                <button type="reset" name="reset" >Cancel</button>
            </fieldset>
        </form>
    </div>
</body>

