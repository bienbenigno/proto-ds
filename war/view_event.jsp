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
<link href="css/bootstrap.css" rel="stylesheet">
<style>
      body {
        padding-top: 60px; 
      }
      
      .calendar {
		height: 70px;
		width:	55px;
		border-radius: 10px;
		border: 1px solid #505050;
		margin: 5px;
	}
	
	.date {
	  font-size: 2.5em;
	  font-weight: bold;
	  text-align: center;
	}
	
	.month {
	  text-align: center;
	}
	
	.day {
	  padding-top: 5px;
	  font-size: 1em;
	  text-align: center;
	}
      
      .eventName {
		font-size: 2.5em;
		height: 70px;
		width:	auto;
		border-radius: 10px;
		vertical-align: middle;
		display: table-cell;
		padding: 0 10px 0 10px;
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
final Event event = eventDAO.get(Long.valueOf(request.getParameter("id")));
request.setAttribute("event", event);

final EventItemDAO eventItemDAO = new EventItemDAOImpl();
final List<EventItem> items = eventItemDAO.findByEvent(event.getId());
request.setAttribute("items", items);
%>

<jsp:include page="header.jsp" />
<div class="container">
	<ul class="breadcrumb">
	 <li><a href="list_event.jsp">List Events</a> <span class="divider">/</span></li>
	 <li class="active">View Event</li>
	</ul>
	
	<div class="buttons">
		<button class="btn btn" onclick="location.href='edit_event.jsp?id=${event.id}';">Edit Event</button>
		<button class="btn btn-danger" onclick="deleteEvent(${event.id});">Delete Event</button>
	</div>
	<table style="margin-top: 20px;">
		<tr>
			<td>
				<div class="calendar inline">
					<div class="month">${event.month}</div>
					<div class="date">${event.dayOfMonth}</div>
					<div class="day">${event.dayOfWeek}</div>
				</div>
			</td>
			<td>
				<div class="eventName" style="border: none;">
					${event.event}
				</div>
			</td>
		</tr>
	</table>
	<hr />
	<h2>Items</h2>
	<div class="buttons">
		<button class="btn btn" onclick="addItem();">Add Item</button>
	</div>
	<br />
	<table class="table table-striped table-hover">
		<tbody>
		<c:forEach var='item' items='${items}'>
			<tr onclick="location.href='view_item.jsp?id=${item.id}';" style="cursor: pointer;">
				<td style="width: 15%">
					<c:if test='${not empty item.photo}'>
						<img alt="${item.photo}" src="serveImage?blob-key=${item.photo}&size=100" class="img-polaroid" />
					</c:if>
				</td>
				<td>
					<div>
						<div style="font-size: 1.5em; margin-bottom: .5em;">${item.description}</div>
						<div style="font-size: .9em;">${item.createdDatetimeString}</div>
					</div>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
<div id="contentbottom"></div>

<script type="text/javascript">
	function addItem() {
		location.href='edit_item.jsp?mode=add&eventId=' + ${event.id};
	}
	
	function deleteEvent(eventId) {
		if (confirm('Are you sure?')) {
			location.href='deleteEvent?id=' + eventId;
		}
	}
</script>

<script src="js/jquery.js"></script>
<script src="js/bootstrap.js"></script>
</body>
</html>