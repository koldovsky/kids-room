<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" %>

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<!--Bootstrap and jQuery -->

<script type="text/javascript" src="webjars/jquery/2.2.3/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


<link rel='stylesheet' href='webjars/bootstrap/3.3.6/css/bootstrap.min.css'> 

<link rel="stylesheet" href="http://getbootstrap.com/2.3.2/assets/css/bootstrap-responsive.css">

<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>

<link rel='stylesheet' href='resources/css/default.css'>

<link rel='stylesheet' href='resources/css/allkidslist.css'>

<!-- Material design -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/bootstrap.material-design/0.5.9/css/bootstrap-material-design.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.3.0/css/roboto.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.3.0/css/material-fullpalette.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.3.0/css/ripples.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.3.0/js/material.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.3.0/js/ripples.min.js"></script>

<!-- Angular Material requires Angular.js Libraries -->
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-animate.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-aria.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-messages.min.js"></script>

<!-- Angular Material Library -->
<script src="http://ajax.googleapis.com/ajax/libs/angular_material/1.1.0-rc2/angular-material.min.js"></script>
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/angular_material/1.1.0-rc2/angular-material.min.css">


<!-- /Material design -->

</head>


<body>
<script type="text/javascript" src="resources/js/langswitcher.js"></script>
<div class="wrapper">
  <div class="header"><tiles:insertAttribute name="header" /></div>

  <div class="content"><tiles:insertAttribute name="body" /></div>

  <div class="footer"><tiles:insertAttribute name="footer" /></div>
</div>

</body>
</html>
