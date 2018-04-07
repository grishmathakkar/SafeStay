<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find an address</title>
</head>
<body>
	<form action="findlocation" method="post">
		<h1>Search for an address by location</h1>
		<p>
			<label for="location">Location</label>
			<input id="location" name="location" value="${fn:escapeXml(param.location)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="addresscreate"><a href="addresscreate">Create Address</a></div>
	<br/>
	<h1>Matching Address</h1>
        <table border="1">
            <tr>
                <th>Locationx</th>
                <th>Street</th>
                <th>Latitude</th>
                <th>Longitude</th>
                <th>Delete BlogUser</th>
            </tr>
            <c:forEach items="${address}" var="blogUser" >
                <tr>
                    <td><c:out value="${address.getLocation}" /></td>
                    <td><c:out value="${address.getStreet}" /></td>
                    <td><c:out value="${address.getLatitude}" /></td>
                    <td><c:out value="${address.getLongitude}" /></td>
                    <td><a href="userdelete?username=<c:out value="${address.getLocation()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
