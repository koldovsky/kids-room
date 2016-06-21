<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/j_spring_security_check" var="allBookingsPerParentURL" />

<link rel='stylesheet' href='resources/css/report.css'>
<script src="resources/js/myBookings.js"></script>
<script src="resources/js/printMyBookings.js"></script>


<div id="scroller">
    <div class="dateSelector">
        <div id="from-div">
            <h3><spring:message code="report.from" /></h3>
            <input id="from" type="date"></input>
        </div>
        <div id="to-div">
            <h3><spring:message code="report.to" /></h3>
            <input id="to" type="date"></input>
        </div>
    </div>

    <div class="tableDiv">

    <h2>
                                 <spring:message code="report.myBookings" /></br>
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
                    <spring:message code="report.sumTotal" /><p id="sum"></p>
                </h3>

            </caption>

        </table>
        <input id="itemsPerPage" type="hidden" value="10"></input>
                <a id="dlink"  style="display:none;"></a>
                <button id="export" onclick= "tableToExcel('myBookings', 'name')" class="btn btn-raised btn-success waves-effectwaves-light exportButton glyphicon glyphicon-download-alt">
                    &nbsp; <spring:message code="report.download" /> Excel
                </button>
                <button id="print" class="btn btn-raised btn-danger exportButton glyphicon glyphicon-print">
                                    &nbsp; <spring:message code="report.print" />
                                </button>

    </div>
</div>
