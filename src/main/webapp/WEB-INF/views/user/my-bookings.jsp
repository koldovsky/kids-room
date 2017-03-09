<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/j_spring_security_check" var="allBookingsPerParentURL" />
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/user-my-report.css'>
<script src="${pageContext.request.contextPath}/resources/js/myBookings.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/printMyBookings.js"></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/resources/js/lib/moment.min.js'></script>

<div id="scroller">
    <div class="dateSelector form-group-material-blue-400">
        <div id="msg">
        <spring:message code="report.select.period"/>:
        </div>
        <div id="from-div">
          <label for="from"><spring:message code="report.from" /></label>
          <input name="from" id="from" type="date" class="form-control" >

        </div>
        <div id="to-div">
            <label for="from"><spring:message code="report.to" /></label>
            <input id="to" type="date" class="form-control">
        </div>
    </div>
    <div id="errorDate"></div>

    <div class="tableDiv">
        <h2>
           <spring:message code="report.myBookings" />
        </h2>
        <table id="myBookings">
         <thead>

            <tr id="header">
                <th><spring:message code="report.date" /></th>
                <th><spring:message code="report.kid" /></th>
                <th><spring:message code="report.place" /></th>
                <th><spring:message code="report.startTime" /></th>
                <th><spring:message code="report.endTime" /></th>
                <th><spring:message code="report.duration" /></th>
                <th><spring:message code="report.sum" /></th>
            </tr>
         </thead>

            <caption class="captionBottom">
                <h3>
                    <spring:message code="report.sumTotal"/>
                    <p id="sum"></p>
                    <spring:message code="report.currencySymbol" />
                </h3>

            </caption>

        </table>
        <input id="itemsPerPage" type="hidden" value="10">
                <a id="dlink"  style="display:none;"></a>
                <button id="export" onclick= "tableToExcel('myBookings', 'name')" class="btn btn-raised btn-success waves-effectwaves-light exportButton glyphicon glyphicon-download-alt">
                    &nbsp; <spring:message code="report.download" /> Excel
                </button>
                <button id="print" class="btn btn-raised btn-danger exportButton glyphicon glyphicon-print">
                                    &nbsp; <spring:message code="report.print" />
                                </button>

    </div>
</div>
 <script src="${pageContext.request.contextPath}/resources/js/validation/validation-my-booking.js"></script>
