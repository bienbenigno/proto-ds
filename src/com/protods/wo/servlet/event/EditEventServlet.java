package com.protods.wo.servlet.event;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.protods.wo.dao.EventDAO;
import com.protods.wo.dao.impl.EventDAOImpl;
import com.protods.wo.model.Event;

public class EditEventServlet extends HttpServlet {

	private static final long serialVersionUID = 2593022685060433398L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final EventDAO eventDAO = new EventDAOImpl();
		final String id = req.getParameter("id");
		final Event event = eventDAO.get(Long.valueOf(id));
		req.setAttribute("event", event);
	}

}
