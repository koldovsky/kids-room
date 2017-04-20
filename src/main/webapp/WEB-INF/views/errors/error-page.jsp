<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div  class="message">
     <img class="not-found" src="${pageContext.request.contextPath}/resources/img/${errorFile}.png" />
     <br>
     ${errorCode}
</div>
