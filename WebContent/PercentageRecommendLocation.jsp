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
	<form action="findpercentage" method="post">
		<h1>Find Percentage by Location</h1>
		<p>
			<label for="location">Location</label> <input id="location"
				name="location" value="${fn:escapeXml(param.location)}">
		</p>
		<p>
			<input type="submit"> <br /> <br /> <br /> 
		</p>
	</form>
	<br />
	<h1>Percentage</h1>
	<c:out value="${prl}"> Here</c:out>
</body>
</html>
