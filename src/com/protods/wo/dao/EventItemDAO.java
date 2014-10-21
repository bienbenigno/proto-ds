package com.protods.wo.dao;

import java.util.List;

import com.protods.wo.model.EventItem;

public interface EventItemDAO extends DAO<EventItem> {

	List<EventItem> findByEvent(Long eventId);
	
}
