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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create a User stay history</title>
</head>
<body>
	<h1>Add Stay History</h1>
	<form action="useraddresshistory" method="post">

		<p>
			<label for="username">Username</label> <input id="username"
				name="username" value="">
		</p>



		<%!String driverName = "com.mysql.jdbc.Driver";%>
		<%!String url = "jdbc:mysql://localhost:3306/SafeStay";%>
		<%!String user = "root";%>
		<%!String psw = "Prachi26";%>
		<%
			Connection con = null;
			PreparedStatement ps = null;
			try {
				Class.forName(driverName);
				con = DriverManager.getConnection(url, user, psw);
				String sql = "SELECT location,street FROM Address";
				ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
		%>
		<p>
			Select Location : <select name="location" id="location">
				<%
					while (rs.next()) {
							String location = rs.getString("location");
							String street = rs.getString("street");
				%>
				<option value="<%=location%>"><%=location%><%=street%></option>
				<%
					}
				%>
			</select>
		</p>
		<%
			} catch (SQLException sqe) {
				out.println(sqe);
			}
		%>

		<p>
			<label for="startdate">StartDate</label> <input id="startdate"
				name="startdate" value="">
		</p>

		<p>
			<label for="enddate">EndDate</label> <input id="enddate"
				name="enddate" value="">
		</p>

		<p>
			<input type="submit">
		</p>
	</form>

</body>
</html>