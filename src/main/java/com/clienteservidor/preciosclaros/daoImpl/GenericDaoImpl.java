package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.clienteservidor.preciosclaros.beanutils.Query;
import com.clienteservidor.preciosclaros.model.GenericEntity;
import com.clienteservidor.preciosclaros.model.Product;
 
public abstract class GenericDaoImpl<T extends GenericEntity> {
	
	protected Class<T> entityClass;
	
//	@PersistenceContext
//	private EntityManager entityManager;
 
    @Autowired
    private SessionFactory sessionFactory;
 
    protected Session getSession() {
    	//return (Session) entityManager.getDelegate();
    	return sessionFactory.getCurrentSession();
    }

	@SuppressWarnings("unchecked")
	public T findById(int id) {
        return (T) getSession().get(entityClass, id);
	}
 
    public void persist(T entity) {
    	entity.setVersion(0);
        getSession().persist(entity);
    }
 
    public void update(T entity) {
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