<%@page import="com.protods.wo.dao.EventItemDAO"%>
<%@page import="com.protods.wo.dao.impl.EventItemDAOImpl"%>
<%@page import="com.protods.wo.model.EventItem"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
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
<title>Item</title>

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
final EventItemDAO eventItemDAO = new EventItemDAOImpl();

final String mode = "add".equals(request.getParameter("mode")) ? "Add" : "Edit";

EventItem eventItem = new EventItem();
if (StringUtils.isNotEmpty(request.getParameter("id"))) {
	eventItem = eventItemDAO.get(Long.valueOf(request.getParameter("id")));
	request.setAttribute("event", eventDAO.get(eventItem.getEventId()));
} else {
	request.setAttribute("event", eventDAO.get(Long.valueOf(request.getParameter("eventId"))));
}
request.setAttribute("eventItem", eventItem);
%>
<jsp:include page="header.jsp" />
<div class="container">
	<ul class="breadcrumb">
	 <li><a href="list_event.jsp">List Events</a> <span class="divider">/</span></li>
	 <li class="active"><a href="view_event.jsp?id=${event.id}">View Event</a><span class="divider">/</span></li>
	 <li class="active">Item</li>
	</ul>
	<h1>${mode} Item for Event ${event.event}</h1>
	<form action="saveItem" method="post" class="form-horizontal">
		<input type="hidden" name="eventId" value="${event.id}" />
		<input type="hidden" name="id" value="<%=request.getParameter("id") %>" />
		<input type="hidden" name="mode" value="<%=request.getParameter("mode") %>" />
		<fieldset>
			<legend>${mode} Item</legend>
				<div class="control-group">
					<label class="control-label">Description</label>
					<div class="controls">
						<input type="text" name="description" value="${eventItem.description}"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Longitude</label>
					<div class="controls">
						<input type="text" name="longitude" value="${eventItem.longitude}"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Latitude</label>
					<div class="controls">
						<input type="text" name="latitude"  value="${eventItem.latitude}" />
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
							<button class="btn">Save</button>
							<button class="btn" onclick="location.href='upload_image.jsp?entityName=EventItem&entityId=${eventItem.id}'; return false;">
							Upload Image</button>
							<button class="btn" onclick="deleteItem(${eventItem.id}); return false;">Delete Item</button>
					</div>
				</div>
		</fieldset>
	</form>
</div>

<script type="text/javascript">
	function addItem() {
		location.href='edit_item.jsp?add=true&eventId=' + ${event.id};
	}
	
	function deleteItem(itemId) {
		if (confirm('Are you sure?')) {
			location.href='deleteItem?id=' + itemId;
		}
	}
</script>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script src="js/bootstrap.js"></script>
</body>
</html>