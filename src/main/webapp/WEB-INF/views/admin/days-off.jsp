<body>
<%
    int x = 10;
    if(x>10) {
%>
<%@include  file="all-days-off.html" %>
<%
    }
%>
<c:forEach var="dayOff" items="${daysOff}">
<td>${dayOff.name}</td>
<td>${dayOff.startDate}</td>
<td class="city">${dayOff.endDate}</td>


</body>