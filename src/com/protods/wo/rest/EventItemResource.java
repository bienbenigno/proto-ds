package com.protods.wo.rest;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.protods.wo.model.EventItem;

public interface EventItemResource {

	@Delete
	void remove();

	@Get
	EventItem retrieve();

	@Post
	void store(EventItem eventItem);

}