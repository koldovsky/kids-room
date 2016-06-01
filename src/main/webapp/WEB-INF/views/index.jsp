<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<link href='resources/css/fullcalendar.css' rel='stylesheet'/>
<link href='resources/css/fullcalendar.print.css' rel='stylesheet' media='print'/>


<script src='resources/js/moment.min.js'></script>

<script src='resources/js/fullcalendar.min.js'></script>
<script src='resources/js/jquery.min.js'></script>
<script src='resources/js/fullcalendar.js'></script>

<script src='resources/js/userCalendar.js'></script>

<script src='resources/js/renderCalendar.js'></script>
<script type='text/javascript' src='resources/js/uk.js'></script>

<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">


<link href='resources/css/formForCalendar.css' rel='stylesheet'/>


<script type="text/javascript" src="resources/js/jquery.timepicker.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/jquery.timepicker.css"/>



<body>
<sec:authorize access="isAuthenticated()">
    Your email <sec:authentication property="principal.username"/></p>
</sec:authorize>

<sec:authorize access="hasRole('USER')">
    <p> I am USER</p>


    <c:forEach items="${managersRoom}" var="r">

        ${r.id}

    </c:forEach>


    <select id="selectBoxUser" onchange="selectRoomForUser(value);">

        <option value=" "></option>

        <c:forEach items="${rooms}" var="r">

            <option value="${r.id}">${r.city}: ${r.address}</option>

        </c:forEach>

    </select>

    <div id='user-calendar'></div>


</sec:authorize>
<sec:authorize access="hasRole('MANAGER')">
    <p> I am MANAGER</p>

    <c:forEach items="${managersRoom}" var="r">

        ${r.id}

    </c:forEach>


    <select id="selectBox" onchange="changeFunc(value);">

        <option value=" "></option>

        <c:forEach items="${rooms}" var="r">

            <option value="${r.id}">${r.city}: ${r.address}</option>

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
                        <div class="allDay">
                            <label><input type="checkbox" id="checkbox" value="">   All day</label>
                        </div>

                        <div class="form-group">
                            <label for="title">Start date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="title" placeholder="startDate" readonly>
                            </div>
                            <div class="col-xs-5">
                                <input id="basicExample" type="text" class="time form-control" size="6"/>
                            </div>
                        </div>
                        <br>


                        <div class="form-group">
                            <label for="endDate">End date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="endDate" placeholder="endDate" readonly>
                            </div>
                            <div class="col-xs-5">
                                <input id="ender" type="text" class="time form-control" size="6"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <label for="Description">Description</label>
                            <textarea type="text" class="form-control" id="description"
                                      placeholder="description"></textarea>
                        </div>
                        <div class="col-xs-6">
                            <button type="button" class="btn btn-success" id="creating">Create</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="container">
        <div class="vertical-center-row">
            <div align="center">
                <div id="updating" hidden>
                    <form id="updatingForm">
                        <div class="form-group">
                            <label for="titleUpdate">Event title</label>
                            <input type="text" class="form-control" id="titleUpdate" placeholder="title">
                        </div>


                        <div class="form-group">
                            <label for="startDayUpdate">Start date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="startDayUpdate" placeholder="startDate" readonly>
                            </div>
                            <div class="col-xs-5">
                                <input id="startTimeUpdate" type="text" class="time form-control" size="6"/>
                            </div>
                        </div>
                        <br>


                        <div class="form-group">
                            <label for="endDateUpdate">End date</label>
                            <br>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="endDateUpdate" placeholder="endDate" readonly>
                            </div>
                            <div class="col-xs-5">
                                <input id="endTimeUpdate" type="text" class="time form-control" size="6"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <label for="descriptionUpdate">Description</label>
                            <textarea type="text" class="form-control" id="descriptionUpdate"
                                      placeholder="description"></textarea>
                        </div>

                            <button type="button" class="btn btn-success" id="updatingButton">Update</button>
                            <button type="button" class="btn btn-danger" id="deleting">Delete</button>

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