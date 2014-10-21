package com.protods.wo.dao;

import java.util.Collection;

import com.protods.wo.model.BaseModel;

public interface DAO<T extends BaseModel> {

	T get(Long id);
	
	T findByExternalId(String externalId);
	
	Collection<T> getAll();
	
	void save(T o);
	
	void delete(T event);
	
	Collection<T> findByEmail(final String email);
}
