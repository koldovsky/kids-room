<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link href='${pageContext.request.contextPath}/resources/css/fullcalendar.css' rel='stylesheet'/>
<link href='${pageContext.request.contextPath}/resources/css/fullcalendar.print.css' rel='stylesheet' media='print'/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">

<link href='${pageContext.request.contextPath}/resources/css/formForCalendar.css' rel='stylesheet'/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery.timepicker.css"/>
<link href='${pageContext.request.contextPath}/resources/css/flow-form.css' rel='stylesheet'/>
<link href='${pageContext.request.contextPath}/resources/css/manager-no-rooms.css' rel='stylesheet'/>

<link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/dataTables.bootstrap4.min.css"/>


<sec:authorize access="hasRole('USER')">
</sec:authorize>

<div class="loading" hidden>Loading&#8230;</div>
<sec:authorize access="hasRole('MANAGER')">

</sec:authorize>
<sec:authorize access="hasRole('ADMINISTRATOR')">

</sec:authorize>


<%--error-dialog--%>
<div id="error-dialog" type="hidden"></div>
<div id="warning-dialog" type="hidden"></div>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src='${pageContext.request.contextPath}/resources/js/userCalendar.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/moment.min.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/jquery.timepicker.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/lib/fullcalendar.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/seriousColorLegendUpdate.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/constants/manager-create-events-constants.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/validation/event-validator.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/validation/user-create-booking-validator.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/single-booking.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/renderCalendar.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/validation/eventValidator.js'></script>
<script src='${pageContext.request.contextPath}/resources/js/validation/recurrent-cancel-validator.js'></script>
<c:choose>
    <c:when test="${pageContext.response.locale=='ua'}">
        <script src="${pageContext.request.contextPath}/resources/js/lib/callendar-ua.min.js"></script>
    </c:when>
</c:choose>
