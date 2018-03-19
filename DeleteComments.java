package SafeStay.src.servlet;
//package SafeStay.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import SafeStay.dal.*;
//import SafeStay.model.*;
import SafeStay.src.SafeStay.dal.*;
import SafeStay.src.SafeStay.model.*;
@WebServlet("/deletecomments")
public class DeleteComments extends HttpServlet {
	
	protected CommentsDao commentsDao;
	
	@Override
	public void init() throws ServletException {
		commentsDao = CommentsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Comment");        
        req.getRequestDispatcher("/DeleteComment.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String commentId = req.getParameter("CommentId");
        
        Comments comment = new Comments(Integer.parseInt(commentId));
        try {
        	comment = commentsDao.delete(comment);
        	
        	// Update the message.
	        if (comment == null) {
	            messages.put("title", "Successfully deleted ");
	            //messages.put("disableSubmit", "true");
	        } else {
	        	messages.put("title", "Failed to delete ");
	        	//messages.put("disableSubmit", "false");
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        /*if (reviewId == null || reviewId.trim().isEmpty()) {
            messages.put("title", "Invalid reviewId");
            messages.put("disableSubmit", "true");
        } else {
	        
        }*/
        
        req.getRequestDispatcher("/DeleteReview.jsp").forward(req, resp);
    }

}