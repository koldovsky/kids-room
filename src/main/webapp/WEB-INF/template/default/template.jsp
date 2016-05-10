<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" %>
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript" src="webjars/jquery/2.2.3/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<link rel='stylesheet' href='webjars/bootstrap/3.3.6/css/bootstrap.min.css'>

<link rel="stylesheet" href="http://getbootstrap.com/2.3.2/assets/css/bootstrap-responsive.css">
<link href="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet"/>
<script src="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/js/bootstrap-editable.min.js"></script>
<link rel='stylesheet' href='resources/css/default.css'>
<link rel='stylesheet' href='resources/css/allkidslist.css'>
<link rel='stylesheet' href='resources/css/listBookedChildren.css'>

</head>
<body>
<div class="wrapper">
  <div class="header"><tiles:insertAttribute name="header" /></div>

  <div class="content"><tiles:insertAttribute name="body" /></div>

  <div class="footer"><tiles:insertAttribute name="footer" /></div>
</div>

</body>
</html>
