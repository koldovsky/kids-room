<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="tophead">
    <div class="logo">
        <a href="/home">
            <img src="resources/img/logo.png" width="300"/>
        </a>
    </div>
</div>

<nav class="navbar navbar-material-blue-500">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#top_navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div id="top_navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><sec:authorize access="!isAuthenticated()">
                <li><a href="<c:url value="/login" />">
                    <span class="glyphicon glyphicon-log-in"></span>
                    <spring:message code="user.login"/>
                </a>
                </li>

                <li><a href="<c:url value="/registration" />">
                    <span class="glyphicon glyphicon-pencil"></span>
                    <spring:message code="user.registration"/>
                </a></li>
                </sec:authorize>


                <sec:authorize access="isAuthenticated()">

                    <sec:authorize access="hasRole('USER')">
                        <li class="dropdown menu-item" style="padding-right: 150px;">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <span style="font-size: 8px;" class="glyphicon glyphicon-triangle-bottom"></span>

                                <span id="usersRoom"> <spring:message code="user.selectRoom"/>:</span>

                            </a>
                            <ul id="selectRoomForParent" class="dropdown-menu">
                                <c:forEach items="${rooms}" var="room">
                                    <li>
                                        <a style="cursor: pointer;"
                                           id="${room.id},${userId},${room.capacity},${room.phoneNumber},${room.managers} ">${room.city}, ${room.address}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                        <li><a href="/home">
                            <span class="glyphicon glyphicon-calendar"></span>
                            <spring:message code="user.calendar"/>
                        </a></li>
                    </sec:authorize>

                    <sec:authorize access="hasRole('MANAGER')">

                        <li id="roompicker" class="dropdown menu-item" style="padding-right: 150px;">

                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <span style="font-size: 8px;" class="glyphicon glyphicon-triangle-bottom"></span>
                                <spring:message code="manager.roompicker"/>:
                                <span id="room"></span>
                            </a>
                            <ul id="selectRoom" class="dropdown-menu">
                                <c:forEach items="${rooms}" var="room">
                                    <li>
                                        <a style="cursor: pointer;" id="${room.id}">${room.address}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>

                        <li><a href="/home">
                            <span class="glyphicon glyphicon-calendar"></span>
                            <spring:message code="user.calendar"/>
                        </a></li>
                    </sec:authorize>

                    <sec:authorize access="hasRole('USER')">
                        <li><a href="mykids">
                            <span class="glyphicon glyphicon-user"></span>
                            <spring:message code="user.myKids"/>
                        </a></li>
                        <li><a href="mybookings">
                            <span class="glyphicon glyphicon-tasks"></span>
                            <spring:message code="user.myBookings"/>
                        </a></li>
                    </sec:authorize>


                    <sec:authorize access="hasRole('MANAGER')">
                        <li><a href="manager-report">
                            <span class="glyphicon glyphicon-tasks"></span>
                            <spring:message code="manager.report"/></a>
                        </li>
                        <li><a href="manager-edit-booking">
                            <span class="glyphicon glyphicon-list"></span>
                            <spring:message code="manager.listbooking"/>
                            <span id="amountOfChildren" class="badge"></span>
                        </a></li>

                    </sec:authorize>

                    <sec:authorize access="hasRole('ADMINISTRATOR')">
                        <li><a href="adm-statistics">
                            <span class="glyphicon glyphicon-tasks"></span>
                            <spring:message code="administrator.statistics"/></a>
                        </li>
                        <li><a href="adm-edit-room">
                            <span class="glyphicon glyphicon-home"></span>
                            <spring:message code="administrator.editRooms"/>
                        </a></li>
                        <li><a href="adm-edit-manager">
                            <span class="glyphicon glyphicon-user"></span>
                            <spring:message code="administrator.editManagers"/>
                        </a></li>
                        <li><a href="adm-config">
                            <span class="glyphicon glyphicon-cog"></span>
                            <spring:message code="administrator.configuration"/>
                        </a></li>
                    </sec:authorize>

                </sec:authorize>


            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown menu-item">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <span class="glyphicon glyphicon-globe"></span>
                        <spring:message code="user.language"/>
                        <span style="font-size: 8px;" class="glyphicon glyphicon-triangle-bottom"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="langitem" id="EN">EN</a></li>
                        <li><a class="langitem" id="UA">UA</a></li>
                    </ul>
                </li>

                <li><sec:authorize access="isAuthenticated()">
                    <a>
                        <form:form action="logout" method="post">
                            <button id="logout" action="submit" type="submit"><span
                                    class="glyphicon glyphicon-log-out"></span><spring:message code="user.logout"/>
                            </button>
                        </form:form>
                    </a>
                </sec:authorize></li>

            </ul>

        </div>
    </div>
</nav>
