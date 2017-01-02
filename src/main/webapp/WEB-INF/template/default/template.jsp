
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" %>

<title><tiles:insertAttribute name="title" ignore="true" /></title>
  <meta name="viewport" content="width=device-width"/>
<!--Bootstrap and jQuery -->

  <script src="https://code.jquery.com/jquery-2.2.4.min.js"
          integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
  <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>

  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
          integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
  <script src="resources/js/lib/ui-bootstrap-tpls-2.1.3.min.js"></script>

  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<link rel="stylesheet" href="http://getbootstrap.com/2.3.2/assets/css/bootstrap-responsive.css">
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"
integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>

<link rel='stylesheet' href='resources/css/default.css'>
<link rel='stylesheet' href='resources/css/allkidslist.css'>
    <link rel="icon" href="resources/img/favicon.png">
<!-- Material design -->

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.3.0/css/material-fullpalette.min.css">
<script type="text/javascript"
src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/4.0.1/bootstrap-material-design.iife.js"></script>

<!-- Angular Material requires Angular.js Libraries -->
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-animate.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-aria.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-messages.min.js"></script>

<!-- Angular Material Library -->
<script src="http://ajax.googleapis.com/ajax/libs/angular_material/1.1.0-rc2/angular-material.min.js"></script>
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/angular_material/1.1.0-rc2/angular-material.min.css">

<link href='resources/css/highlighting.css' rel='stylesheet'/>

</head>


<body>
<script type="text/javascript" src="resources/js/constants/general_constants.js"></script>
<script type="text/javascript" src='resources/js/error.js'></script>
<script type="text/javascript" src="resources/js/langswitcher.js"></script>

<c:choose>
  <c:when test="${pageContext.response.locale =='ua'}">
    <script src="${pageContext.request.contextPath}/resources/js/constants/messages-ua.js"></script>
  </c:when>
  <c:when test="${pageContext.response.locale == 'en'}">
    <script src="${pageContext.request.contextPath}/resources/js/constants/messages.js"></script>
  </c:when>
</c:choose>

<div class="wrapper">
  <div class="header"><tiles:insertAttribute name="header" /></div>

  <div class="content"><tiles:insertAttribute name="body" /></div>

  <div class="footer"><tiles:insertAttribute name="footer" /></div>
</div>

</body>
</html>
