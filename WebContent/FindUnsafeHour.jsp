<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find the most unsafe hour</title>
</head>
<body>
	<form action="findunsafehour" method="post">
		<h1>Search for an Unsafe Hour by Location</h1>
		<p>
			<label for="location">Location</label> <input id="location"
				name="location" value="${fn:escapeXml(param.location)}">
		</p>
		<p>
			<input type="submit"> <br /> <br /> <br /> 
		</p>
	</form>
	<br />
	<h1>Unsafe Hours</h1>
	<table border="1">
		<tr>
			<th>Hour</th>
			<th>Location</th>
		</tr>
<%-- 		<c:forEach items="${hours}" var="hour"> --%>
			<tr>
				<td><c:out value="${hours.getHour()}" /></td>
				<td><c:out value="${hours.getLocation()}" /></td>
			</tr>
<%-- 		</c:forEach> --%>
	</table>
</body>
</html>
