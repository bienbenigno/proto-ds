<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %><%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %><%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	String uploadURL = blobstoreService.createUploadUrl("/upload_image");
	uploadURL = uploadURL.replace("virus-server", "192.168.254.104");
%><%= uploadURL %>