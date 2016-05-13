<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<link href='resources/css/fullcalendar.css' rel='stylesheet' />
<link href='resources/css/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='resources/js/moment.min.js'></script>
<script src='resources/js/jquery.min.js'></script>
<script src='resources/js/fullcalendar.min.js'></script>

<link href='resources/css/calendarstyle.css' rel='stylesheet' />
<script src='resources/js/renderCalendar.js'></script>

<div>
        <sec:authorize access="isAuthenticated()">
            Your email <sec:authentication property="principal.username" /></p>
        </sec:authorize>

        <sec:authorize access="hasRole('USER')">
            <p> I am USER</p>
            <h2><a href="mykids">My Kids</a></h2>



        </sec:authorize>
        <sec:authorize access="hasRole('MANAGER')">
            <p> I am MANAGER</p>


            <select id="selectBox" onchange="changeFunc(value);">

                <option value=" " > </option>

                <c:forEach items="${rooms}" var="r">

                        <option value="${r.id}" >${r.address}</option>

                </c:forEach>
            </select>

            <div id='calendar'></div>
        </sec:authorize>
        <sec:authorize access="hasRole('ADMINISTRATOR')">
             <p> I am ADMINISTRATOR</p>
        </sec:authorize>

</div>