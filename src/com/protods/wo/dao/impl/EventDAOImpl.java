package com.protods.wo.dao.impl;


import com.protods.wo.dao.EventDAO;
import com.protods.wo.model.Event;

public class EventDAOImpl extends BaseDAOImpl<Event> implements EventDAO {

	@Override
	Class<Event> getClazz() {
		return Event.class;
	}

}
