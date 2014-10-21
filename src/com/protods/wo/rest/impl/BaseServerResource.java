package com.protods.wo.rest.impl;

import org.restlet.resource.ServerResource;

import com.protods.wo.dao.DAO;
import com.protods.wo.model.BaseModel;

public abstract class BaseServerResource extends ServerResource {
	
	@SuppressWarnings("rawtypes")
	protected abstract DAO getDAO(); 
	
	@SuppressWarnings("unchecked")
	public void importEntity(final BaseModel externalEntity) {
		BaseModel persistentEntity = getDAO().findByExternalId(externalEntity.getExternalId());
		if (persistentEntity != null) {
			externalEntity.setId(persistentEntity.getId());
		}
		externalEntity.setSynched(Boolean.TRUE);
		getDAO().save(externalEntity);
	}

}
