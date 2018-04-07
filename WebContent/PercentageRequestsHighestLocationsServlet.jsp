<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>most requested areas: request count and percentage</title>
</head>
<body>
	<h1>Location along with request count and the percentage it
		accounts for:</h1>
	<table border="1">
		<tr>
			<th>Location</th>
			<th>Request Count</th>
			<th>Total Count</th>
			<th>Percentage</th>
		</tr>
		<c:forEach items="${locations}" var="locations">
			<tr>
				<td><c:out value="${locations.getLocStreet()}" /></td>
				<td><c:out value="${locations.getHighRequest()}" /></td>
				<td><c:out value="${locations.getTotalRequest()}" /></td>
				<td><c:out value="${locations.getPercentage()}" /></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>