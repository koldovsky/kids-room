<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>

<c:url value="/j_spring_security_check" var="myKidsUrl" />

<%@ page import="ua.softserveinc.tc.constants.ChildConstants" %>

<link rel='stylesheet' href='resources/css/mykidslist.css'>
<script src='resources/js/profile.js'></script>

<div class="kidsCard" data-id="${kid.id}" modelAttribute="<%=ChildConstants.View.MY_KIDS_LIST_ATTRIBUTE %>">

    <div class="left">
        <div id="Photo">
        <a href="images/${kid.id}">
        <img id="picture" alt="Profile picture" src="images/${kid.id}" width="200" height="200" />
        </a>
        </div>

<sec:authorize access="hasRole('USER')">
         <form method="POST" modelAttribute="fileForm" action="uploadImage/${kid.id}" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <label class="btn btn-raised btn-success glyphicon glyphicon-folder-open">
                     &nbsp;<spring:message code="user.selectPhoto" />  <input path="file" type="file" accept=".jpg, .png"
                     id="file-upload" name="file" style="display: none;">

                 </label>
                    <input id="file-submit" style="display: none;" type="submit" data-bfi-disabled>

                 <form:errors path="file" cssClass="error" />
         </form>
         <p id="image-msg" style="display: none;">
         <spring:message code="kid.image.msg" /></p>
         </sec:authorize>

        </div>

 <h2> ${kid.firstName} ${kid.lastName} </h2>

<hr></hr>
<h3> <spring:message code="kid.date" />: ${kid.dateOfBirth}<h3>
<h3> <spring:message code="kids.age" />: ${kid.getAge()}<h3>
<h4> <spring:message code="kid.comment" />: ${kid.comment} </h4>

 <sec:authorize access="hasRole('USER')">
<button id="edit" class="btn btn-raised btn-info glyphicon glyphicon-pencil">
                &nbsp;<spring:message code="button.edit" />
        </button>

</sec:authorize>
<hr></hr>
<div id="parent">
    <h3> <spring:message code="kid.parent" /> </h4>
    <h4> ${kid.getParentId().getFullName()}<h4>
    <h4> ${kid.getParentId().getEmail()}<h4>
    <h4> ${kid.getParentId().getPhoneNumber()}<h4>
</div>

<div style="clear:both"></div>

</div>

