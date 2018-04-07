<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add an Address</title>
</head>
<body>
	<h1>Add Address</h1>
	<form action="addresscreate" method="post">
		<p>
			<label for="location">Location</label>
			<input id="location" name="location" value="">
		</p>
		<p>
			<label for="street">Street</label>
			<input id="street" name="street" value="">
		</p>
		<p>
			<label for="longitude">Longitude</label>
			<input id="longitude" name="longitude" value="">
		</p>
		<p>
			<label for="latitude">Latitude</label>
			<input id="latitude" name="latitude" value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>
