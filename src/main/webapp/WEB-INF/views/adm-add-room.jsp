
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Add location</title>

	<link rel="stylesheet" type="text/css" href="resources/css/admin-style.css">
</head>

<body>
    <div class="for-table">
        <table class="for-table-fields">
        <form  class="for-table" action="adm-add-room" method="post" modelAttribute="city">

           <tr><th>
               <legend class="for-field"><strong>Add room</strong></legend>
           </th></tr>

           <tr><td>
               <div class="form-group">
                  <label class="for-field"> Room name <input type="text" name="name" required
                                                       class="form-control"/></label>
               </div>

               <div class="form-group">
                  <label class="for-field"> Room capacity <input type="number" name="capacity" required
                                                           class="form-control"/></label>
               </div>

               <div class="form-group">
                  <label class="for-field"> Room address <input type="text" name="address" required
                                                          class="form-control"/></label>
               </div>

               <div class="form-group">
                  <label class="for-field"> City <input type="text" name="city" required class="form-control"/></label>
                  </label>
               </div>

               <div class="form-group">
                  <label class="for-field"> Room phone number <input type="text" name="phoneNumber"
                                                               pattern="^(\+38|8|)(\W*\d){10}\W*$"
                                                               required class="form-control"/></label>
               </div>

               <div class="form-group">
                  <label class="for-field"> Room-manager
                     <select name="managers" required class="form-control" id="mySelect" id="sel1">
                        <c:forEach var="manager" items="${managerList}" >
                           <option value="${manager.id}">${manager.firstName} ${room.manager.lastName}</option>
                        </c:forEach>
                     </select>
                  </label>
               </div>


              <div ng-app="angularjs-starter" ng-controller="MainCtrl">
              <div class="form-group">
                 <label class="for-field">Room rates
                     <fieldset  data-ng-repeat="choice in choices">
                     <label class="for-field1">
                        <input id="myText" type="text" ng-model="choice.hourRate" key="" placeholder="Hour" class="form-control">
                     </label>
                     <label class="for-field2">
                        <input id="myText" type="text" ng-model="choice.priceRate" value="" placeholder="Price" class="form-control">
                     </label>
                        <button class="remove" ng-show="$last" ng-click="removeChoice()">-</button>
                     </fieldset>
                 </label>

                 <button type="button" class="addfields" ng-click="addNewChoice()" >+</button>

                 <div id="choicesDisplay">
                    {{ choices }}
                 </div>
              </div>


               <div class="form-group">
                  <button class="btn btn-raised btn-primary btn-lg glyphicon glyphicon-ok" type="submit" name="submit"></button>
                  <button class="btn btn-raised btn-primary btn-lg glyphicon glyphicon-remove" type="reset" name="reset" ></button>
               </div>

                 <script src="resources/js/for-rates.js"></script>
              </div>

           </td></tr>
        </form>
        </table>
    </div>
</body>
</html>

