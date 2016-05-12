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
    <a href="adm-add-manager"><button name="add-manager">Add</button></a>
    <form action="adm-edit-manager" method="post" value="movie">
    <button name="delete-manager">Delete</button>
    </form>

    <form action="adm-edit-manager" method="get" modelAttribute="managers">
        <div class="rightback">
            <div class="contentback">
                <div class="leftback">
                    <div class="leftsidebar">
                        <fieldset class="possition">
                            <label>
                                <legend>Manager list</legend>
                                <select name="managers" required>
                                    <c:forEach var="manager" items="${managerList}" >
                                        <option value="${manager}">${movie}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </fieldset>
                    </div>

                    <div class="rightsidebar">

                    </div>
                </div>
            </div>
        </div>
    </form>
</body>
</html>