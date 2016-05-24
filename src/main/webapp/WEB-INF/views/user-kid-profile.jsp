<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:url value="/j_spring_security_check" var="myKidsUrl" />

<%@ page import="ua.softserveinc.tc.constants.ModelConstants.MyKidsConst" %>

<link rel='stylesheet' href='resources/css/mykidslist.css'>
<script src='resources/js/profile.js'></script>

<div class="kidsCard" data-id="${kid.id}" modelAttribute="<%=MyKidsConst.MY_KIDS_LIST_ATTRIBUTE %>">
 <div class="col-md-4">
    <div class="left">
        <div id="Photo">
        <a href="images/${kid.id}.jpg">
        <img id="picture" alt="Profile picture" src="images/${kid.id}.jpg" width="200" height="200" />
        </a>
        </div>

         <form method="POST" action="uploadImage/${kid.id}" enctype="multipart/form-data">
                 <label class="btn btn-success glyphicon glyphicon-folder-open">
                     &nbsp;<spring:message code="user.selectPhoto" /> <input type="file" name="file" style="display: none;">
                 </label>
                 <label class="btn btn-danger glyphicon glyphicon-open">
                    <spring:message code="user.upload" /><input style="display: none;" type="submit" data-bfi-disabled>
                 </label>
         </form>
        </div>
 </div>

<div class="col-md-8">
 <h2> ${kid.firstName} ${kid.lastName} </h2>
        <button id="edit" class="btn btn-raised btn-info glyphicon glyphicon-pencil">
                &nbsp;<spring:message code="button.edit" />
        </button>
<h3> <spring:message code="kid.date" />: ${kid.dateOfBirth}<h3>
<h3> <spring:message code="kids.age" />: ${kid.getAge()}<h3>
<h4> <spring:message code="kid.comment" />: ${kid.comment} </h4>
</div>

<div style="clear:both"></div>

</div>

