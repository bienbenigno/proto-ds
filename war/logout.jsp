<%@page import="com.google.appengine.api.users.User"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%
	final UserService userService = UserServiceFactory.getUserService();
	response.sendRedirect(userService.createLogoutURL("/list_event.jsp"));
	return;
%>