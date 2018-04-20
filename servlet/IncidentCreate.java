package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SafeStay.dal.AddressDao;
import SafeStay.dal.IncidentsDao;
import SafeStay.dal.OffenseDao;
import SafeStay.model.Address;
import SafeStay.model.Incidents;
import SafeStay.model.Incidents.Shootings;
import SafeStay.model.Incidents.UCRs;
import SafeStay.model.Offense;

@WebServlet("/incidentcreate")
public class IncidentCreate extends HttpServlet {

	protected IncidentsDao incidentsDao;

	@Override
	public void init() throws ServletException {
		incidentsDao = IncidentsDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		// Just render the JSP.
		req.getRequestDispatcher("/IncidentCreate.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// Create Incident

		OffenseDao offenseDao = OffenseDao.getInstance();
		Offense offense = null;
		try {
			offense = offenseDao.getOffenseByOffenseCode(Integer.parseInt(req.getParameter("offensecode")));
		} catch (SQLException e) {
			// TODO: handle exception
		}

		String district = req.getParameter("district");
		String reportingArea = req.getParameter("reportingarea");
		int reportingAreaInt = Integer.parseInt(reportingArea);
		Shootings shooting = Shootings.valueOf(req.getParameter("shooting"));
		String occuredOnDate = req.getParameter("occuredondate");
		Timestamp ts = Timestamp.valueOf(occuredOnDate);
		String dayOfWeek = req.getParameter("dayofweek");
		int hour = Integer.parseInt(req.getParameter("hour"));
		UCRs ucr = UCRs.valueOf(req.getParameter("ucr"));
		String location = req.getParameter("location");
		AddressDao addressDao = AddressDao.getInstance();
		Address address = null;
		try {
			address = addressDao.getAddressByLocation(location);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Incidents incidents = new Incidents(offense, district, reportingAreaInt, shooting, ts, dayOfWeek, hour, ucr,
					address);
			incidents = incidentsDao.create(incidents);
			messages.put("success", "Successfully created incident " + incidents.getIncidentId());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		req.getRequestDispatcher("/IncidentCreate.jsp").forward(req, resp);
	}
}
