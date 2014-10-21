package com.protods.wo.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.protods.wo.dao.EventItemDAO;
import com.protods.wo.dao.impl.EventItemDAOImpl;
import com.protods.wo.model.EventItem;

public class SaveEventItemServlet extends HttpServlet {

	private static final long serialVersionUID = -7574033446323725864L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final UserService userService = UserServiceFactory.getUserService();
		final User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
			return;
		}
		
		final EventItemDAO eventItemDAO = new EventItemDAOImpl();
		final Long eventId = Long.valueOf(req.getParameter("eventId"));
		EventItem item = new EventItem();
		if ("add".equals(req.getParameter("mode"))) {
			item.setEventId(eventId);
		} else {
			item = eventItemDAO.get(Long.valueOf(req.getParameter("id")));
		}
		
		item.setDescription(req.getParameter("description"));
		item.setLongitude(req.getParameter("longitude"));
		item.setLatitude(req.getParameter("latitude"));
		
		eventItemDAO.save(item);
		resp.sendRedirect("view_event.jsp?id=" + eventId);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

}
