
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
  <%@ page contentType="text/html; charset=UTF-8" %>

  <title><tiles:insertAttribute name="title" ignore="true"/></title>
  <meta name="viewport" content="width=device-width"/>
  <!--Bootstrap and jQuery -->

  <script src="${pageContext.request.contextPath}/resources/js/lib/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/lib/tether.min.js"></script>
  <script src='${pageContext.request.contextPath}/resources/js/lib/jquery-ui.min.js'></script>

  <link rel="stylesheet" href="https://getbootstrap.com/2.3.2/assets/css/bootstrap-responsive.css">
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

  <script src="${pageContext.request.contextPath}/resources/js/lib/angular.min.js"></script>

  <link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/default.css'>
  <link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/allkidslist.css'>
  <link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon.png">
  <!-- Material design -->

  <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.3.0/css/material-fullpalette.min.css">
  <script type="text/javascript"
          src="${pageContext.request.contextPath}/resources/js/lib/bootstrap-material-design.iife.js"></script>

  <!-- Angular Material requires Angular.js Libraries -->
  <script src="${pageContext.request.contextPath}/resources/js/lib/angular-animate.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/lib/angular-aria.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/lib/angular-messages.min.js"></script>

  <!-- Angular Material Library -->
  <script src="${pageContext.request.contextPath}/resources/js/lib/angular-material.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/lib/angular-material.min.css">

  <link href='${pageContext.request.contextPath}/resources/css/highlighting.css' rel='stylesheet'/>

</head>


<body>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/constants/general_constants.js"></script>
<script src='${pageContext.request.contextPath}/resources/js/error.js' type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/langswitcher.js" type="text/javascript"></script>

<c:choose>
  <c:when test="${pageContext.response.locale =='ua'}">
    <script src="${pageContext.request.contextPath}/resources/js/constants/messages-ua.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/lib/messages_uk.js"></script>
  </c:when>
  <c:when test="${pageContext.response.locale == 'en'}">
    <script src="${pageContext.request.contextPath}/resources/js/constants/messages.js"></script>
  </c:when>
</c:choose>

<div class="wrapper">
  <div class="header"><tiles:insertAttribute name="header"/></div>

  <div class="content"><tiles:insertAttribute name="body"/></div>

  <div class="footer"><tiles:insertAttribute name="footer"/></div>
</div>

</body>
</html>
