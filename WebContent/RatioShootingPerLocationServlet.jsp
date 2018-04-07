<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ratio of Shooting incidents per location</title>
</head>
<body>
	<h1>Location along shooting incident ratio:</h1>
	<table border = "1">
	<tr>
		<th>Location</th>
		<th>Ratio</th>
	</tr>
	<c:forEach items="${locations}" var="locations">
	<tr>
		<td><c:out value="${locations.getLocation()}" /></td>
		<td><c:out value="${locations.getRatio()}" /></td>
	</tr>
	</c:forEach>
	
	</table>
</body>
</html>