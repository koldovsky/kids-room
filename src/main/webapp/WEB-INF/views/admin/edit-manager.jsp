<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">


<body>
   <div class="tableDiv for-table">
       <table class="for-table">
          <tr class="hide-border">
             <th colspan="8" class="set-standard-color">
                <legend class="for-table"><strong><spring:message code="administrator.editManagers"/></strong></legend>
             </th>
          </tr>
          <tr></tr>
          <tr>
             <th><strong><spring:message code="administrator.manager.email"/></strong></th>
             <th><strong><spring:message code="administrator.manager.firstName"/></strong></th>
             <th><strong><spring:message code="administrator.manager.lastName"/></strong></th>
             <th><strong><spring:message code="administrator.phoneNumber"/></strong></th>
             <th><strong><spring:message code="administrator.manager.confirmedAccount"/></strong></th>
             <th><strong><spring:message code="administrator.manager.activeAccount"/></strong></th>
             <th><strong><spring:message code="administrator.edit"/></strong></th>
             <th><strong><spring:message code="administrator.block"/></strong></th>
          </tr>

          <c:forEach var="manager" items="${managerList}">
          <tr>
             <td>${manager.email}</td>
             <td>${manager.firstName}</td>
             <td>${manager.lastName}</td>
             <td>${manager.phoneNumber}</td>
             <td>
                 <c:if test="${manager.confirmed eq true}">
                    <img src="resources/img/ok.png" class="img-size">
                 </c:if>
                 <c:if test="${manager.confirmed ne true}">
                    <img src="resources/img/no.png" class="img-size">
                 </c:if>
             </td>
             <td>
                <c:if test="${manager.active eq true}">
                    <img src="resources/img/ok.png" class="img-size">
                 </c:if>
                 <c:if test="${manager.active ne true}">
                    <img src="resources/img/no.png" class="img-size">
                 </c:if>
             </td>
             <td><a href="adm-update-manager?id=${manager.id}">
                    <button class="btn btn-info btn-raised glyphicon glyphicon-pencil active"></button></a></td>

             <td>
                <c:url var="lockUrl" value="/adm-edit-manager?id=${manager.id}"/>
                <form:form id="${managerFormId}" action="${lockUrl}" method="POST" >
                   <input id="manager" name="manager" type="hidden" value="${manager.id}" />
                   <button type="submit" value="lock" onClick="return confirm('sure?')"
                            class="btn btn-raised btn-warning glyphicon glyphicon-sort active for-lock-button"></button>
                </form:form>
             </td>
          </tr>
          </c:forEach>

          <tr></tr>
          <tr>
             <td colspan="8" class="hide-border set-standard-color">
                <a href="adm-add-manager"><input type="button" value=<spring:message code="administrator.add"/>
                                           class="btn btn-raised btn-primary waves-effect waves-light active"/></a>
             </td>
          </tr>
       </table>
    </div>
</body>