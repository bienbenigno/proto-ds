<%@page import="com.protods.wo.model.EventItem"%>
<%@page import="com.protods.wo.dao.impl.EventItemDAOImpl"%>
<%@page import="com.protods.wo.dao.EventItemDAO"%>
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
<%

final UserService userService = UserServiceFactory.getUserService();
final User user = userService.getCurrentUser();
if (user == null) {
	response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
	return;
}

final EventItemDAO eventItemDAO = new EventItemDAOImpl();
String itemId = request.getParameter("id");
if (StringUtils.isEmpty(itemId) && request.getSession().getAttribute("itemId") != null) {
	itemId = request.getSession().getAttribute("itemId").toString();
} else {
	request.getSession().setAttribute("itemId", itemId);
}

if (StringUtils.isEmpty(itemId)) {
	response.sendRedirect("/list_event.jsp");
	return;
}

final EventItem item = eventItemDAO.get(Long.valueOf(itemId));
request.setAttribute("item", item);
request.setAttribute("eventId", item.getEventId());
%>

<body>
<jsp:include page="header.jsp" />
<div class="container">
	<ul class="breadcrumb">
	 <li><a href="list_event.jsp">List Events</a> <span class="divider">/</span></li>
	 <li class="active"><a href="view_event.jsp?id=${eventId}">View Event</a><span class="divider">/</span></li>
	 <li class="active">Item</li>
	</ul>
	<input type="hidden" name="eventId" value="${eventId}" />
	<input type="hidden" name="id" value="<%=request.getParameter("id") %>" />
	<input type="hidden" name="mode" value="<%=request.getParameter("mode") %>" />
	<div>
		<button class="btn btn-primary" onclick="location.href='edit_item.jsp?id=${item.id}&mode=Edit'">Edit Item</button>
	</div>
	<div style="margin: auto; text-align: center;">
		<div>
			<div>
				<h2>${item.description}</h2>
				<div style="font-size: .9em;">${item.createdDatetimeString}</div>
			</div>
		</div>
		<c:if test="${not empty item.photo}">
			<div>
				<div>
						<img alt="${item.photo}" src="serveImage?blob-key=${item.photo}" 
								style="cursor: pointer;"
								class="img-polaroid"
								onclick="location.href='serveImage?blob-key=${item.photo}'" />
				</div>
			</div>
		</c:if>
	</div>
	<br>
	<div class="container">
	<div id="map_canvas" style="width:500px; height:300px; margin: auto; text-align: center;"></div>
	</div>
	<div style="height: 50px;">&nbsp;</div>
</div>

<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDH7JkaAn41VW7EcRwC2bxBK2Uvxjc4OgQ&sensor=false"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script src="js/bootstrap.js"></script>

<script type="text/javascript">
	$(document).ready(function () {
		loadMap();
	});
	
	function loadMap() {
	  var longitude = "${item.longitude}";
	  var latitude = "${item.latitude}";
	  if (latitude && longitude) {
		  var myLatlng = new google.maps.LatLng(latitude, longitude);
		  var mapOptions = {
		      center: myLatlng,
		      zoom: 18,
		      mapTypeId: google.maps.MapTypeId.HYBRID
			};
		  var map = new google.maps.Map(document.getElementById("map_canvas"),
	        mapOptions);
		  var marker = new google.maps.Marker({
		      position: myLatlng,
		      map: map,
		      title:"${item.description}",
		      animation: google.maps.Animation.DROP
		  });
	  }
	}

	function addItem() {
		location.href='edit_item.jsp?add=true&eventId=' + ${eventId};
	}
</script>

</body>
</html>