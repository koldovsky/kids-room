<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:url value="/j_spring_security_check" var="myKidsUrl" />

<%@ page import="ua.softserveinc.tc.constants.ModelConstants.MyKidsConst" %>

<link rel='stylesheet' href='resources/css/mykidslist.css'>

<script src="resources/js/mykidslist.js">
</script>


<div class="list" modelAttribute="<%=MyKidsConst.MY_KIDS_LIST_ATTRIBUTE %>">
<h2>
    <spring:message code="kid.list" />
</h2>
<c:forEach var="kid" items="${kids}">
<div class="kidslistblock">
    <div class="kidslistitem">
    <h3>${kid.getFullName()}</h3>
    </div>
    <button class="btn btn-raised btn-info glyphicon glyphicon-pencil">
        &nbsp;<spring:message code="button.edit" />
    </button>
    <div class="kidinfo" data-id="${kid.getId()}">
            <h4>
                <spring:message code="kid.date" />: ${kid.getDateOfBirth()}
            </h4>
            <p>
                ${kid.getComment()}
            </p>
    </div>
</div>

</c:forEach>
<form action="registerkid">
    <button class="btn btn-raised btn-primary waves-effect waves-light" type="submit">
        <spring:message code="button.add" />
    </button>
</form>
</div>

