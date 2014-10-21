package com.protods.wo.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.protods.wo.dao.EMF;
import com.protods.wo.model.BaseModel;
import com.protods.wo.model.EventItem;

public abstract class BaseDAOImpl<T extends BaseModel> {
	
	abstract Class<T> getClazz();
	
	public T get(Long id) {
		final EntityManager em = EMF.get().createEntityManager();
		try {
			return em.find(getClazz(), id);
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public T findByExternalId(final String externalId) {
		final EntityManager em = EMF.get().createEntityManager();
		try {
			System.out.print("Finding " + getClazz().getSimpleName() + " by externalId '" + externalId + "'. \t");
//			final List<T> resultList = em.createQuery(
//					"select from " + getClazz().getName() + " where externalId = '" 
//							+ externalId + "'").getResultList();
//			if (resultList.size() > 0) {
//				return resultList.get(0);
//			}
			final Query query = em.createQuery("select from " + 
					getClazz().getName() + " where externalId = :externalId and (deleted is null or deleted = false)");
			query.setParameter("externalId", externalId);
			final List<T> resultList = query.getResultList();
			T result = null;
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
			System.out.println("Result: '" + result + "'.");
			return result;
		} finally {
			em.close();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection<T> getAll() {
		final EntityManager em = EMF.get().createEntityManager();
		try {
			final List resultList = em.createQuery("select from " + getClazz().getName()).getResultList();
			resultList.size();//TODO: 
			return resultList;
		} finally {
			em.close();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<T> findByEmail(final String email) {
		final EntityManager em = EMF.get().createEntityManager();
		try {
			final List resultList = em.createQuery(
					"select from " + getClazz().getName() + " where email = '" 
							+ email + "' and (deleted is null or deleted = false)").getResultList();
			resultList.size();//TODO:
			return resultList;
		} finally {
			em.close();
		}
	}

	public void save(T o) {
		final EntityManager em = EMF.get().createEntityManager();
		em.persist(o);
		em.close();
	}

	public void delete(final T o) {
		final EntityManager em = EMF.get().createEntityManager();
		em.remove(o);
		em.close();
	}

}