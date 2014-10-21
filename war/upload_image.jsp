<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>

<html>
    <head>
        <title>Upload Test</title>
    </head>
    <body>
        <form action="<%= blobstoreService.createUploadUrl("/upload_image") %>" method="post" enctype="multipart/form-data">
        	<input type="hidden" name="entityId" value="<%=request.getParameter("entityId") %>">
        	<input type="hidden" name="entityName" value="<%=request.getParameter("entityName") %>">
            <input type="file" name="myFile">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>