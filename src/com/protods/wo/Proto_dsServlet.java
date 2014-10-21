package com.protods.wo;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.*;

import com.protods.wo.dao.EventDAO;
import com.protods.wo.dao.impl.EventDAOImpl;
import com.protods.wo.model.Event;

@SuppressWarnings("serial")
public class Proto_dsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
