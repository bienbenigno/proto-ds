package com.protods.wo.rest;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.protods.wo.model.Event;

public interface EventResource {

	@Delete
	void remove();

	@Get
	Event retrieve();

	@Post
	void store(Event event);

}