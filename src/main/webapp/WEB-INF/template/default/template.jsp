
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
<script type="text/javascript" src="resources/js/js.js"></script>
<link rel="stylesheet" href="http://getbootstrap.com/2.3.2/assets/css/bootstrap-responsive.css">
<link rel='stylesheet' href='resources/css/default.css'>
<link rel='stylesheet' href='resources/css/allkidslist.css'>

</head>
<body>
<div class="wrapper">
  <div class="header"><tiles:insertAttribute name="header" /></div>

  <div class="content"><tiles:insertAttribute name="body" /></div>

  <div class="footer"><tiles:insertAttribute name="footer" /></div>
</div>

</body>
</html>