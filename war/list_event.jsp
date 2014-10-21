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
	.eventName:focus { box-shadow: 0 0 10px red; }
	
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
request.setAttribute("events", eventDAO.findByEmail(user.getEmail()));
%>

<jsp:include page="header.jsp" />

<div class="container">
	<h1>Events</h1>
	<div class="buttons">
		<button class="btn btn-primary" type="button" onclick="location.href='edit_event.jsp?mode=Add';">Add Event</button>
	</div>
	<br />
	<table class="table table-striped table-hover">
		<tbody>
			<c:forEach var='event' items='${events}'>
				<tr style="cursor: pointer;" onclick="goToEvent(${event.id});">
					<td style="width: 60px;">
						<div class="calendar">
							<div class="month">${event.month}</div>
							<div class="date">${event.dayOfMonth}</div>
							<div class="day">${event.dayOfWeek}</div>
						</div>
					</td>
					<td>
						<div class="eventName">
							${event.event}
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<script type="text/javascript">
	function goToEvent(id) {
		location.href = 'view_event.jsp?id=' + id;		
	}
</script>

<script src="js/jquery.js"></script>
<script src="js/bootstrap.js"></script>
</body>
</html>