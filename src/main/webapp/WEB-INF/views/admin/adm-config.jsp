<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page import="ua.softserveinc.tc.constants.AdminConstants" %>

<link rel='stylesheet' href='resources/css/admin-config.css'>

<form:form class = "config"  enctype="application/x-www-form-urlencoded"
modelAttribute="<%=AdminConstants.ATR_CONFIG %>" action="adm-config" method="post">

<img id="config-logo"src="resources/img/config.png"/>
<h3 id="title"><spring:message code="administrator.configuration" /></h3>

</span>
<div class="form-group">
             <label for="kidsMinAge" class="required"><spring:message code="properties.kidsMinAge" /></label>
              <form:input path="kidsMinAge" id="" value="${config.kidsMinAge}"
              class="primary-color form-control" required="required"/>
              <form:errors path="kidsMinAge" cssClass="error" />

             <label for="kidsMaxAge" class="required"><spring:message code="properties.kidsMaxAge" /></label>
              <form:input path="kidsMaxAge" id="" value="${config.kidsMaxAge}"
              class="primary-color form-control" required="required"/>
              <form:errors path="kidsMaxAge" cssClass="error" />
              </div>

<h4><spring:message code="properties.calculation" /></h4>
<div class="form-group">
            <label for="hourToCalculateBookingsEveryDay" class="required"><spring:message code="properties.hours" /></label>
                          <form:input path="hourToCalculateBookingsEveryDay" id="" value="${config.hourToCalculateBookingsEveryDay}"
                          class="primary-color form-control" required="required"/>
                          <form:errors path="hourToCalculateBookingsEveryDay" cssClass="error" />

            <label for="minutesToCalculateBookingsEveryDay" class="required"><spring:message code="properties.minutes" /></label>
                          <form:input path="minutesToCalculateBookingsEveryDay" id="" value="${config.minutesToCalculateBookingsEveryDay}"
                          class="primary-color form-control" required="required"/>
                          <form:errors path="minutesToCalculateBookingsEveryDay" cssClass="error" />
  </div>


<div class="form-group">
<h4><spring:message code="properties.emailreport" /></h4>
            <label for="dayRep" class="required"><spring:message code="properties.day" /></label>
                                      <form:input path="dayToSendEmailReport" id="" value="${config.dayToSendEmailReport}"
                                      class="primary-color form-control" required="required"/>
                                      <form:errors path="dayToSendEmailReport" cssClass="error" />

            <label for="hoursRep" class="required"><spring:message code="properties.hours" /></label>
                                      <form:input path="hourToSendEmailReport" id="" value="${config.hourToSendEmailReport}"
                                      class="primary-color form-control" required="required"/>
                                      <form:errors path="hourToSendEmailReport" cssClass="error" />

            <label for="minsRep" class="required"><spring:message code="properties.minutes" /></label>
                                      <form:input path="minutesToSendEmailReport" id="" value="${config.minutesToSendEmailReport}"
                                      class="primary-color form-control" required="required"/>
                                      <form:errors path="minutesToSendEmailReport" cssClass="error" />

</div>



<div class="form-group">
<label for="minPeriod" class="required"><spring:message code="properties.minPeriod" /></label>
                          <form:input path="minPeriodSize" id="" value="${config.minPeriodSize}"
                          class="primary-color form-control" required="required"/>
                          <form:errors path="minPeriodSize" cssClass="error" />
</div>

<div class="form-group">
<label for="serverName" class="required"> <spring:message code="properties.servername" /> </label>
                          <form:input path="serverName" id="" value="${config.serverName}"
                          class="primary-color form-control" required="required"/>
                          <form:errors path="serverName" cssClass="error" />
</div>

      <button class="btn btn-raised btn-success" type="submit" name="action">
        <spring:message code="user.submit" />
      </button>

</form:form>
