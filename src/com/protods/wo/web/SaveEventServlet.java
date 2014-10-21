package com.protods.wo.web;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

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

public class SaveEventServlet extends HttpServlet {
	
	private static final long serialVersionUID = -8182722732705440492L;

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
		
		final EventDAO eventDAO = new EventDAOImpl();
		
		final String eventName = req.getParameter("name");
		if (StringUtils.isNotEmpty(eventName)) {
			final String eventId = req.getParameter("id");
			Event event = new Event();
			if (StringUtils.isNotEmpty(eventId)) {
				event = eventDAO.get(Long.valueOf(eventId));
			} else {
				event.setCreatedDate(new Date());
				event.setEvent(eventName);
				event.setExternalId(UUID.randomUUID().toString());
				event.setEmail(user.getEmail());
			}
			
			event.setEvent(eventName);
			event.setLastModifiedDate(new Date());
			eventDAO.save(event);
			
			resp.sendRedirect("view_event.jsp?id=" + event.getId());
		} else {
			throw new RuntimeException("Event name is empty.");
		}
	}

}
