<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recommendations</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>RecommendationId</th>
                <th>Rating</th>
                <th>PetFriendly</th>
                <th>ChildFriendly</th>
                <th>UserName</th>
                <th>Location</th>
                <th>Add</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="${recommendations}" var="recommendation" >
                <tr>
                    <td><c:out value="${recommendation.getRecommendationId()}" /></td>
                    <td><c:out value="${recommendation.getRating()}" /></td>
                    <td><c:out value="${recommendation.getPetfriendly()}" /></td>
                    <td><c:out value="${recommendation.getChildfriendly()}" /></td>
                    <td><c:out value="${recommendation.getEndusers().getUserName()}" /></td>
                    <td><c:out value="${recommendation.getAddress().getLocation()}" /></td>
                    <td><a href="recommendationcreate?recommendationid=<c:out value="${recommendation.getRecommendationId()}"/>">Add</a></td>
                    <td><a href="deleterecommendation?recommendationid=<c:out value="${recommendation.getRecommendationId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
