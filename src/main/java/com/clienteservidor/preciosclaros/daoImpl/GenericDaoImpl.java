package com.clienteservidor.preciosclaros.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.clienteservidor.preciosclaros.Exceptions.EntityNotFoundException;
import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.beanutils.Query;
import com.clienteservidor.preciosclaros.model.GenericEntity;
 
public abstract class GenericDaoImpl<T extends GenericEntity> {
	
	protected Class<T> entityClass;
 
    @Autowired
    private SessionFactory sessionFactory;
 
    protected Session getSession() {
    	return sessionFactory.getCurrentSession();
    }

	@SuppressWarnings("unchecked")
	public T findById(int id) {
        T entity = (T) getSession().get(entityClass, id);
        
        if(entity == null) {
        	throw new EntityNotFoundException();
        }
        
        return entity;
	}
 
    public T persist(T entity) {
    	entity.setVersion(0);
        getSession().persist(entity);
        return entity;
    }
 
    public void update(T e) {
    	/*
    	Integer version = findById(e.getId()).getVersion();
    	e.setVersion(version);
    	getSession().merge(e);
    	*/
    	T entity = findById(e.getId());
    	MyBeanUtils.copyProperties(e, entity);
    	getSession().update(entity);
    }
 
    public void delete(T entity) {
        getSession().delete(entity);
    }

    protected void setOffset(Criteria criteria, Integer offset) {
    	if(offset != null) {
    		criteria.setFirstResult(offset);
    	}
    }

    protected void setLimit(Criteria criteria, Integer limit) {
    	if(limit != null) {
    		criteria.setMaxResults(limit);
    	}
    }
    
    protected void filterEq(Criteria criteria, String attribute, Object object){
    	if(object != null) {
    		criteria.add(Restrictions.eq(attribute, object));
    	}
    }

    public Criteria getCriteria() {
		return getSession().createCriteria(this.entityClass);
    }

    public Query getQuery() {
    	return new Query(getSession(), entityClass);
    }
}