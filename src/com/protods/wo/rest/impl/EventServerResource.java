package com.protods.wo.rest.impl;

import java.util.logging.Logger;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.protods.wo.dao.DAO;
import com.protods.wo.dao.EventDAO;
import com.protods.wo.dao.impl.EventDAOImpl;
import com.protods.wo.model.Event;
import com.protods.wo.rest.EventResource;

/**
 * The server side implementation of the Event resource.
 */
public class EventServerResource extends BaseServerResource implements EventResource {
	
	private static final Logger log = Logger.getLogger(EventServerResource.class.getName());

    private EventDAO eventDAO = new EventDAOImpl();
    
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
    public Event retrieve() {
        return eventDAO.findByExternalId(getReference().getLastSegment());
    }

    /* (non-Javadoc)
	 * @see com.protods.wo.rest.impl.EventResource#store(com.protods.wo.model.Event)
	 */
    @Post
    public void store(Event event) {
    	log.info("Saving Event '" + event.getEvent() + "' with ExternalID: '" + event.getExternalId() + "'");
    	this.importEntity(event);
    }

	@Override
	protected DAO<Event> getDAO() {
		return eventDAO;
	}

}
