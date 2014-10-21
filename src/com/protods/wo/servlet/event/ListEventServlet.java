package com.protods.wo.servlet.event;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.protods.wo.dao.EventDAO;
import com.protods.wo.dao.impl.EventDAOImpl;

public class ListEventServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(ListEventServlet.class.getName());

	private static final long serialVersionUID = 6426968686740365349L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final UserService userService = UserServiceFactory.getUserService();
		final User user = userService.getCurrentUser();
		if (user == null) {
			log.info("Got no user. Redirecting...");
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
			return;
        }
		log.info("Logged user: " + user.getEmail());
		
		final EventDAO eventDAO = new EventDAOImpl();
		req.setAttribute("events", eventDAO.getAll());
	}
	
	
}
