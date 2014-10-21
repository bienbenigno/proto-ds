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
import com.protods.wo.dao.EventItemDAO;
import com.protods.wo.dao.impl.EventItemDAOImpl;
import com.protods.wo.model.EventItem;

public class DeleteItemServlet extends HttpServlet {
	
	private static final long serialVersionUID = 3250883989576790529L;

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
		
		final EventItemDAO eventItemDAO = new EventItemDAOImpl();
		
		final String eventItemId = req.getParameter("id");
		if (StringUtils.isNotEmpty(eventItemId)) {
			final EventItem eventItem = eventItemDAO.get(Long.valueOf(eventItemId));
			final Long eventId = eventItem.getEventId();
			eventItem.setDeleted(Boolean.TRUE);
			eventItemDAO.save(eventItem);
			resp.sendRedirect("view_event.jsp?id=" + eventId);
		} else {
			throw new RuntimeException("Event Item ID is empty.");
		}
	}

}
