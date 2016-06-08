<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
<link rel="stylesheet" type="text/css" href="resources/css/bookings.css">


<div class="for-table">
    <table class="for-table-fields">
    <form:form class = "adm-edit-manager" modelAttribute="manager" action="adm-update-manager" method="post">

        <tr><th>
            <legend class="for-field"><strong>Update manager</strong></legend>
        </th></tr>

        <tr><td>
            <form:hidden path="id" />
            <form:hidden path="password" id="password" value="${manager.password}"/>

            <div class="form-group">
               <label for="email" class="for-field">Email
                  <form:input path="email" id="email" value="${manager.email}" class="form-control" required="required"/>
               </label>
            </div>

            <div class="form-group">
               <label for="firstName" class="for-field">First Name
                  <form:input path="firstName" id="firstName" value="${manager.firstName}" class="form-control"
                                                              required="required"/>
               </label>
            </div>

            <div class="form-group">
               <label for="lastName" class="for-field">Last Name
                  <form:input path="lastName" id="lastName" value="${manager.lastName}" class="form-control"
                                                            required="required"/>
               </label>
            </div>

            <div class="form-group">
               <label for="phoneNumber" class="for-field">Phone number
                  <form:input path="phoneNumber" id="phoneNumber" value="${manager.phoneNumber}"
                              pattern="^(\+38|8|)(\W*\d){10}\W*$" class="form-control" required="required"/>
               </label>
            </div>

            <div class="form-group">
                <button type="submit" name="submit" class="btn btn-raised btn-info glyphicon glyphicon-ok"></button>
                <button type="reset" name="reset" class="btn btn-raised btn-danger glyphicon glyphicon-remove"
                        onclick="window.location.href='/home/adm-edit-manager'"></button>
            </div>
        </th></tr>

    </form:form>
    </table>
</div>
