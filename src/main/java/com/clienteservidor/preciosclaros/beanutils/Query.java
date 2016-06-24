package com.clienteservidor.preciosclaros.beanutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.clienteservidor.preciosclaros.model.Product;

public class Query {
	private Criteria criteria;
	private FullTextSession fullTextSession = null;
	private org.apache.lucene.search.Query query = null;
	private org.hibernate.search.FullTextQuery fullTextQuery;
	private Session session;
	private Class<?> cls;

	private FullTextSession getFullTextSession() {

		if(fullTextSession == null) {
			fullTextSession = Search.getFullTextSession(session);
		}
		
		return fullTextSession;
	}
	
	public Query(Session session, Class<?> cls) {
		this.session = session;
		this.cls = cls;
		this.criteria = session.createCriteria(cls);
	}

    public Query setOffset(Integer offset) {
    	if(offset != null) {
    		criteria.setFirstResult(offset);
    	}
    	return this;
    }

    public Query setLimit(Integer limit) {
    	if(limit != null) {
    		criteria.setMaxResults(limit);
    	}
    	return this;
    }
    
    public Query filterEq(String attribute, Object object){
    	if(object != null) {
    		criteria.add(Restrictions.eq(attribute, object));
    	}
    	return this;
    }
    
    public Query search(String search, String... fields) {
    	if(search == null) {
    		return this;
    	}

    	query = getFullTextSession().getSearchFactory().buildQueryBuilder()
    			.forEntity(cls).get().keyword().onFields(fields)
    			.matching(search).createQuery();

    	return this;
    }
    
    public List getResults() {
    	if(query == null) {
    		return criteria.list();
    	}
		
    	fullTextQuery = getFullTextSession().createFullTextQuery(query, cls);
    	fullTextQuery.setCriteriaQuery(criteria);

    	return fullTextQuery.list();
    }
}
