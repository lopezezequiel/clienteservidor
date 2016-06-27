package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.dao.UnitDao;
import com.clienteservidor.preciosclaros.model.Unit;

@Repository("unitDao")
public class UnitDaoImpl extends GenericDaoImpl<Unit> implements UnitDao {
	
	public UnitDaoImpl() {
		this.entityClass = Unit.class; 
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Unit> findAll(Integer offset, Integer limit, String query) {
		return (Collection<Unit>) getQuery().setOffset(offset).setLimit(limit)
				.search(query, "singular", "plural")
				.getResults();
	}

}