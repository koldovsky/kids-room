<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<link href='resources/css/fullcalendar.css' rel='stylesheet'/>
<link href='resources/css/fullcalendar.print.css' rel='stylesheet' media='print'/>


<script src='resources/js/moment.min.js'></script>

<script src='resources/js/fullcalendar.min.js'></script>
<script src='resources/js/jquery.min.js'></script>
<script src='resources/js/fullcalendar.js'></script>

<script src='resources/js/userCalendar.js'></script>

<!--
<link href='resources/css/calendarstyle.css' rel='stylesheet' />
-->

<script src='resources/js/renderCalendar.js'></script>
<script type='text/javascript' src='resources/js/uk.js'></script>

<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link href='resources/css/formForCalendar.css' rel='stylesheet'/>


<body>
<sec:authorize access="isAuthenticated()">
    Your email <sec:authentication property="principal.username"/></p>
</sec:authorize>

<sec:authorize access="hasRole('USER')">
    <p> I am USER</p>
    <h2><a href="mykids">My Kids</a></h2>


    <c:forEach items="${managersRoom}" var="r">

        ${r.id}

    </c:forEach>


    <select id="selectBox" onchange="selectRoomForUser(value);">

        <option value=" "></option>

        <c:forEach items="${rooms}" var="r">

            <option value="${r.id}">${r.address}</option>

        </c:forEach>

    </select>

    <div id='calendar'></div>


</sec:authorize>
<sec:authorize access="hasRole('MANAGER')">
    <p> I am MANAGER</p>

    <c:forEach items="${managersRoom}" var="r">

        ${r.id}

    </c:forEach>


    <select id="selectBox" onchange="changeFunc(value);">

        <option value=" "></option>

        <c:forEach items="${rooms}" var="r">

            <option value="${r.id}">${r.address}</option>

        </c:forEach>

    </select>


    <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="dialog" hidden>
                    <form id="form">
                        <div class="form-group">
                            <label for="startDate">Event title</label>
                            <input type="text" class="form-control" id="startDate" placeholder="title">
                        </div>


                        <div class="form-group">
                            <label for="title">Start date</label>
                            <input type="text" class="form-control" id="title" placeholder="startDate">
                        </div>

                        <div class="form-group">
                            <label for="endDate">End date</label>
                            <input type="text" class="form-control" id="endDate" placeholder="endDate">
                        </div>
                        <div class="form-group">
                            <label for="Description">Description</label>
                            <textarea type="text" class="form-control" id="description"
                                      placeholder="description"></textarea>
                        </div>
                        <button type="button" class="btn btn-success" id="creating">Create</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div id='calendar'></div>

</sec:authorize>
<sec:authorize access="hasRole('ADMINISTRATOR')">
    <p> I am ADMINISTRATOR</p>
</sec:authorize>

</body>