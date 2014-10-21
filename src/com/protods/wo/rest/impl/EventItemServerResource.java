package com.protods.wo.rest.impl;

import java.util.logging.Logger;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.protods.wo.dao.DAO;
import com.protods.wo.dao.EventDAO;
import com.protods.wo.dao.EventItemDAO;
import com.protods.wo.dao.impl.EventDAOImpl;
import com.protods.wo.dao.impl.EventItemDAOImpl;
import com.protods.wo.model.Event;
import com.protods.wo.model.EventItem;
import com.protods.wo.rest.EventItemResource;

/**
 * The server side implementation of the EventItem resource.
 */
public class EventItemServerResource extends BaseServerResource 
		implements EventItemResource {
	
	private static final Logger log = Logger.getLogger(EventItemServerResource.class.getName());
	
	private EventDAO eventDAO = new EventDAOImpl();

    private EventItemDAO eventItemDAO = new EventItemDAOImpl();
    
    /* (non-Javadoc)
	 * @see com.protods.wo.rest.impl.EventResource#remove()
	 */
    @Delete
    public void remove() {
    }

    /* (non-Javadoc)
	 * @see com.protods.wo.rest.impl.EventResource#retrieve()
	 */
    @Get
    public EventItem retrieve() {
        return eventItemDAO.findByExternalId(getReference().getLastSegment());
    }

    /* (non-Javadoc)
	 * @see com.protods.wo.rest.impl.EventResource#store(com.protods.wo.model.Event)
	 */
    @Post
    public void store(EventItem eventItem) {
    	log.info("Saving EventItem '" + eventItem.getDescription() + "' + with External ID: '" + eventItem.getExternalId() + "'");
    	EventItem persistentEventItem = getDAO().findByExternalId(eventItem.getExternalId());
		if (persistentEventItem != null) {
			//eventItem.setId(persistentEventItem.getId());
		}
		eventItem.setSynched(Boolean.TRUE);

		final Event event = eventDAO.findByExternalId(eventItem.getEventExternalId());
    	eventItem.setEventId(event.getId());
    	eventItemDAO.save(eventItem);
    }

	@Override
	protected DAO<EventItem> getDAO() {
		return eventItemDAO;
	}

}
