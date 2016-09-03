<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:url value="/j_spring_security_check" var="myKidsUrl" />

<%@ page import="ua.softserveinc.tc.constants.ChildConstants" %>

<link rel='stylesheet' href='resources/css/mykidslist.css'>

<script src="resources/js/mykidslist.js"></script>


<div class="list" modelAttribute="<%=ChildConstants.View.MY_KIDS_LIST_ATTRIBUTE %>">

<c:if test="${kids.isEmpty()}">
    <div class="start-page">
        <h1>
          <span class="label label-primary" id="radius">
          <spring:message code="kids.welcome" /></span>
        </h1>
        <h3 class="add-kid-button"><spring:message code="kids.emptyList" /></h3>
        <form action="registerkid">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn btn-raised btn-success waves-effect waves-light" type="submit" class="add-kid-button">
                <spring:message code="button.add" />
            </button>
        </form>
    </div>
</c:if>

<c:if test="${!kids.isEmpty()}">
    <img id="icon-png" src="resources/img/kids_icon.png" />
    <h2>
        <spring:message code="kid.list" />
    </h2>
    <c:forEach var="kid" items="${kids}">
        <div data-id="${kid.getId()}" class="kidslistblock">
            <div class="kidslistitem">
                <div class="thumb">
                    <img id="picture" alt="Profile picture" src="images/${kid.id}" width="100" height="100" />
                </div>
                <h3>${kid.getFullName()}</h3>
            </div>

        </div>
    </c:forEach>
    <form action="registerkid">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-raised btn-success waves-effect waves-light" type="submit">
            <spring:message code="button.add" />
        </button>
    </form>
</c:if>


</div>

