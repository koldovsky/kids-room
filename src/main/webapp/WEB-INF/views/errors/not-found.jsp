<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div  class="message">
     <img class="not-found" src="${pageContext.request.contextPath}/resources/img/404.png" />
     <br>
     <spring:message code="error.notFound" />
</div>
