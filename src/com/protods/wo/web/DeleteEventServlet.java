package com.protods.wo.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.protods.wo.dao.EventDAO;
import com.protods.wo.dao.impl.EventDAOImpl;
import com.protods.wo.model.Event;

public class DeleteEventServlet extends HttpServlet {
	
	private static final long serialVersionUID = -8182722732705440492L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final UserService userService = UserServiceFactory.getUserService();
		final User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
			return;
        }
		
		final EventDAO eventDAO = new EventDAOImpl();
		
		final String eventId = req.getParameter("id");
		if (StringUtils.isNotEmpty(eventId)) {
			final Event event = eventDAO.get(Long.valueOf(eventId));
			event.setDeleted(Boolean.TRUE);
			eventDAO.save(event);
			resp.sendRedirect("list_event.jsp");
		} else {
			throw new RuntimeException("Event ID is empty.");
		}
	}

}
