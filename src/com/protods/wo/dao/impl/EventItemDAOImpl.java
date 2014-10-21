package com.protods.wo.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.protods.wo.dao.EMF;
import com.protods.wo.dao.EventItemDAO;
import com.protods.wo.model.EventItem;

public class EventItemDAOImpl extends BaseDAOImpl<EventItem> implements EventItemDAO {
	
	@SuppressWarnings("unchecked")
	public List<EventItem> findByEvent(Long eventId) {
		final EntityManager em = EMF.get().createEntityManager();
		try {
			final Query query = em.createQuery("select from " + 
					EventItem.class.getName() + " where eventId = :eventId and (deleted is null or deleted = false)");
			query.setParameter("eventId", eventId);
			final List<EventItem> resultList = query.getResultList();
			resultList.size();//TODO: fuck this
			return resultList;
		} finally {
			em.close();
		}
	}

	@Override
	Class<EventItem> getClazz() {
		return EventItem.class;
	}

}
