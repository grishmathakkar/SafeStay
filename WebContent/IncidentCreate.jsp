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
<title>Add an Incident</title>
</head>
<body>

	<h1>Add Incident</h1>
	<form action="incidentcreate" method="post">
		<p>
			<label for="offensecode">Offense Code</label> <input id="offensecode"
				name="offensecode" value="">
		</p>
		<p>
			<label for="district">District</label> <input id="district"
				name="district" value="">
		</p>
		<p>
			<label for="reportingarea">Reporting Area</label> <input
				id="reportingarea" name="reportingarea" value="">
		</p>
		<p>
			<label for="shooting">Shooting</label> <input id="shooting"
				name="shooting" value="">
		</p>
		<p>
			<label for="occuredondate">Occured on Date</label> <input
				id="occuredondate" name="occuredondate" value="">
		</p>
		<p>
			<label for="dayofweek">Day of Week</label> <input id="dayofweek"
				name="dayofweek" value="">
		</p>
		<p>
			<label for="hour">Hour</label> <input id="hour" name="hour" value="">
		</p>
		<p>
			<label for="ucr">UCR</label> <input id="ucr" name="ucr" value="">
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
