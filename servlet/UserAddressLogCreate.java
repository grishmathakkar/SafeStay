package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SafeStay.dal.AddressDao;
import SafeStay.dal.EndUsersDao;
import SafeStay.dal.UserAddressLogsDao;
import SafeStay.model.Address;
import SafeStay.model.EndUsers;
import SafeStay.model.Reviews;
import SafeStay.model.UserAddressLogs;

@WebServlet("/useraddresshistory")
public class UserAddressLogCreate extends HttpServlet {
	protected UserAddressLogsDao userAddDao;
	
	@Override
	public void init() throws ServletException {
		userAddDao = UserAddressLogsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        EndUsers endUsers = null;
        // Retrieve and validate name.
        // firstname is retrieved from the URL query string.
        String userName = req.getParameter("username");
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid name.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
//        	try {
//            	endUsers = endUsersDao.getEndUserByUserName(userName);
//            } catch (SQLException e) {
//    			e.printStackTrace();
//    			throw new IOException(e);
//            }
        	messages.put("success", "Displaying results for " + userName);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	//messages.put("previousUserName", userName);
        }
        req.setAttribute("endUsers", endUsers);
        
        req.getRequestDispatcher("/UserAddressLogCreate.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		// Create user stay history
		String userName = req.getParameter("username");
		EndUsersDao endUsersDao = EndUsersDao.getInstance();
		EndUsers endUsers = null;
		String name = null;
		try {
			endUsers = endUsersDao.getEndUserByUserName(userName);
			name = endUsers.getUserName();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		String location = req.getParameter("location");
		AddressDao addressDao = AddressDao.getInstance();
		Address address = null;
		try {
			address = addressDao.getAddressByLocation(location);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		String startdate = req.getParameter("startdate");
		SimpleDateFormat x = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = null;
		try {
			d = x.parse(startdate);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		java.sql.Date start_date = new java.sql.Date(d.getTime());
		
		String enddate = req.getParameter("enddate");
		SimpleDateFormat x1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d1 = null;
		try {
			d1 = x.parse(startdate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		java.sql.Date end_date = new java.sql.Date(d1.getTime());

		try {
			UserAddressLogs ualog = new UserAddressLogs(endUsers, address, start_date, end_date);
			ualog = userAddDao.create(ualog);
			messages.put("success", "Successfully created user addess history by " + endUsers.getUserName());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		req.getRequestDispatcher("/UserAddressLogCreate.jsp").forward(req, resp);
	}
	
}
