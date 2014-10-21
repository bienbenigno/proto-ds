package com.protods.wo.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

public class ServeImageServlet extends HttpServlet {

	private static final long serialVersionUID = -1013720009054167974L;
	
	private static final Logger log = Logger.getLogger(ServeImageServlet.class.getName());
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		final BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		log.info("Serving image with blobkey '" + blobKey + "'");
		if (StringUtils.isNotEmpty(req.getParameter("size"))) {
			final ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey)
					.crop(false).imageSize(Integer.parseInt(req.getParameter("size")));
			final ImagesService imagesService = ImagesServiceFactory.getImagesService();
			res.sendRedirect(imagesService.getServingUrl(options));
		} else {
			blobstoreService.serve(blobKey, res);
		}
	}

}
