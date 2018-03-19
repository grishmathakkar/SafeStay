<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a User</title>
</head>
<body>
	<form action="findadmins" method="post">
		<h1>Search for a Admin by UserName</h1>
		<p>
			<label for="username">UserName</label> <input id="username"
				name="username" value="${fn:escapeXml(param.username)}">
		</p>
		<p>
			<input type="submit"> <br /> <br /> <br /> <span
				id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br />
	<div id="adminCreate">
		<a href="admincreate">Create Admin</a>
	</div>
	<br />
	<h1>Matching Admins</h1>
	<table border="1">
		<tr>
			<th>UserName</th>
			<th>Password</th>
			<th>FirstName</th>
			<th>LastName</th>
			<th>Age</th>
			<th>Email</th>
			<th>Phone</th>
			<th>DeleteAdmin</th>
		</tr>
		<%-- 		<c:forEach items="${endUsers}" var="endUser"> --%>
		<tr>
			<td><c:out value="${admins.getUserName()}" /></td>
			<td><c:out value="${admins.getPassword()}" /></td>
			<td><c:out value="${admins.getFirstName()}" /></td>
			<td><c:out value="${admins.getLastName()}" /></td>
			<td><c:out value="${admins.getAge()}" /></td>
			<td><c:out value="${admins.getEmail()}" /></td>
			<td><c:out value="${admins.getPhone()}" /></td>
			<td><a
				href="admindelete?username=<c:out value="${admins.getUserName()}"/>">Delete</a></td>
		</tr>
		<%-- 		</c:forEach> --%>
	</table>
</body>
</html>
