package com.protods.wo.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

import com.protods.wo.rest.impl.EventItemServerResource;
import com.protods.wo.rest.impl.EventServerResource;



public class RestletServerApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		router.attachDefault(new Directory(getContext(), "war:///"));
		router.attach("/events/", EventServerResource.class, 1);
		router.attach("/eventItems/", EventItemServerResource.class, 1);

		return router;
	}

}
