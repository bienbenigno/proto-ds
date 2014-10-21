package com.protods.wo.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.protods.wo.dao.EventItemDAO;
import com.protods.wo.dao.impl.EventItemDAOImpl;
import com.protods.wo.model.EventItem;

public class UploadImageServlet extends HttpServlet {
	
	private static final long serialVersionUID = -2455609818428720639L;
	
	private static final Logger log = Logger.getLogger(UploadImageServlet.class.getName());
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	
	private EventItemDAO eventItemDAO = new EventItemDAOImpl();

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
    	
    	log.info("Uploading Image...");

        final Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        BlobKey blobKey = null;
        if (blobs.get("myFile") != null && blobs.get("myFile").size() > 0) {
        	blobKey = blobs.get("myFile").get(0);
        }
        
        if (blobKey == null) {
            res.sendRedirect("/");
        } else {
        	EventItem eventItem = null;
        	if (StringUtils.isNotEmpty(req.getParameter("externalId"))) {
            	eventItem = eventItemDAO.findByExternalId(req.getParameter("externalId"));
            } else if (StringUtils.isNotEmpty(req.getParameter("entityId"))) {
            	eventItem = eventItemDAO.get(Long.valueOf(req.getParameter("entityId")));
            }
        	if (eventItem != null) {
        		if (StringUtils.isNotEmpty(eventItem.getPhoto())) {
        			final BlobKey oldBlobKey = new BlobKey(eventItem.getPhoto());
        			try {
        			 blobstoreService.delete(oldBlobKey);
        			} catch (Exception e) {
        				System.out.println("Error while deleting old image: " + e.getMessage());
        			}
         		}
        		
        		log.info("Setting photo of eventItem '" + eventItem.getDescription() + "'. Blobkey: '" + blobKey.getKeyString() + "'");
        		eventItem.setPhoto(blobKey.getKeyString());
        		eventItemDAO.save(eventItem);
        	}
        	
        	if (StringUtils.isNotEmpty(req.getParameter("externalId"))) {
        		return;
        	} else if (StringUtils.isNotEmpty(req.getParameter("entityId")) &&
        			"EventItem".equals(req.getParameter("entityName"))) {
        		res.sendRedirect("/view_item.jsp?id=" + req.getParameter("entityId"));
        	} else {
        		res.sendRedirect("/serveImage?blob-key=" + blobKey.getKeyString());	
        	}
        }
    }
    
}
