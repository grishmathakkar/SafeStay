<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.sql.*"%>
<%
	ResultSet resultset = null;
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add a Comment On Incident</title>
</head>
<body>

	<h1>Add Comment on Incident</h1>
	<form action="commentonincident" method="post">
	<p>
			<label for="username">Username</label> <input id="username"
				name="username" value="">
		</p>

		<p>
			<label for="incidentid">IncidentId</label> <input id="incidentid"
				name="incidentid" value="">
		</p>
		
		<p>
			<label for="description">Content</label> <input id="description"
				name="description" value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<br />
	<br />
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>
