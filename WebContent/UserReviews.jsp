<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reviews</title>
</head>
<body>
	<h1>${messages.title}</h1>
	<table border="1">
		<tr>
			<th>ReviewId</th>
			<th>Content</th>
			<th>Username</th>
			<th>Location</th>
			<th>Add</th>
			<th>Update</th>
			<th>Delete</th>
		</tr>
		<c:forEach items="${reviews}" var="review">
			<tr>
				<td><c:out value="${review.getReviewId()}" /></td>
				<td><c:out value="${review.getContent()}" /></td>
				<td><c:out value="${review.getEndusers().getUserName()}" /></td>
				<td><c:out value="${review.getAddress().getLocation()}" /></td>
				<td><a
					href="reviewcreate?reviewid=<c:out value="${review.getReviewId()}"/>">Add</a></td>
				<td><a
					href="reviewupdate?reviewid=<c:out value="${review.getReviewId()}"/>">Update</a></td>
				<td><a
					href="deletereview?reviewid=<c:out value="${review.getReviewId()}"/>">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
