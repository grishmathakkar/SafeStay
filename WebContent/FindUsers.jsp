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
	<form action="findusers" method="post">
		<h1>Search for a EndUser by UserName</h1>
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
	<div id="userCreate">
		<a href="usercreate">Create EndUser</a>
	</div>
	<br />
	<h1>Matching EndUsers</h1>
	<table border="1">
		<tr>
			<th>UserName</th>
			<th>Password</th>
			<th>FirstName</th>
			<th>LastName</th>
			<th>Age</th>
			<th>Email</th>
			<th>Phone</th>
			<th>DoB</th>
			<th>DeleteUser</th>
			<th>Reviews</th>
			<th>Recommendations</th>
		</tr>
<%-- 		<c:forEach items="${endUsers}" var="endUser"> --%>
			<tr>
				<td><c:out value="${endUsers.getUserName()}" /></td>
				<td><c:out value="${endUsers.getPassword()}" /></td>
				<td><c:out value="${endUsers.getFirstName()}" /></td>
				<td><c:out value="${endUsers.getLastName()}" /></td>
				<td><c:out value="${endUsers.getAge()}" /></td>
				<td><c:out value="${endUsers.getEmail()}" /></td>
				<td><c:out value="${endUsers.getPhone()}" /></td>
				<td><fmt:formatDate value="${endUsers.getDateOfBirth()}"
						pattern="yyyy-MM-dd" /></td>
				<td><a href="userdelete?username=<c:out value="${endUsers.getUserName()}"/>">Delete</a></td>
				<td><a href="userreview?username=<c:out value="${endUsers.getUserName()}"/>">Reviews</a></td>
				<td><a href="userrecommendations?username=<c:out value="${endUsers.getUserName()}"/>">Recommendations</a></td>
			</tr>
<%-- 		</c:forEach> --%>
	</table>
</body>
</html>
