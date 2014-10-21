<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.protods.wo.model.EventItem"%>
<%@page import="com.protods.wo.dao.impl.EventItemDAOImpl"%>
<%@page import="com.protods.wo.dao.EventItemDAO"%>
<%@page import="java.util.List"%>
<%@page import="com.google.appengine.api.users.User"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.protods.wo.model.Event"%>
<%@page import="com.protods.wo.dao.impl.EventDAOImpl"%>
<%@page import="com.protods.wo.dao.EventDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Events</title>

<link rel="stylesheet" type="text/css" href="css/ui-lightness/jquery-ui.css"/>
<link href="css/bootstrap.css" rel="stylesheet">
<style>
      body {
        padding-top: 60px; 
      }
</style>
<link href="css/bootstrap-responsive.css" rel="stylesheet">
</head>
<body>

<%
final UserService userService = UserServiceFactory.getUserService();
final User user = userService.getCurrentUser();
if (user == null) {
	response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
	return;
}

final EventDAO eventDAO = new EventDAOImpl();

Event event = new Event();
String mode = "Add";
String id = "";
if (StringUtils.isNotEmpty(request.getParameter("id"))) {
	event = eventDAO.get(Long.valueOf(request.getParameter("id")));
	mode = "Edit";
	id = request.getParameter("id");
}
request.setAttribute("event", event);
request.setAttribute("mode", mode);
request.setAttribute("id", id);

%>
<jsp:include page="header.jsp" />

<div class="container">
	<ul class="breadcrumb">
	 <li><a href="list_event.jsp">List Events</a> <span class="divider">/</span></li>
	 <li class="active">${mode} Event</li>
	</ul>

	<fieldset>
		<legend>${mode} Event</legend>
		<form class="form-horizontal" action="saveEvent" method="post">
		<input type="hidden" name="id" value="${id}" />
		<div class="control-group">
			<label class="control-label" for="name"">Description</label>
			<div class="controls">
				<input type="text" name="name" placeholder="Description" value="${event.event}">
			</div>
		</div>
		 <div class="control-group">
			<div class="controls">
				<button type="submit" class="btn">Save</button>
				<c:if test="${mode eq 'Edit'}">
					<button class="btn" onclick="return deleteEvent(${event.id});">Delete Event</button>
				</c:if>
			</div>
		</div>
		</form>
	</fieldset>
</div>

<script type="text/javascript">
	$(function() {
		$('#createdDate').datepicker();
	});
	
	function deleteEvent(eventId) {
		if (confirm('Are you sure?')) {
			location.href='deleteEvent?id=' + eventId;
		}
	}
</script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script src="js/bootstrap.js"></script>
</body>
</html>