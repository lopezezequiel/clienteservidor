package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.dao.LocalityDao;
import com.clienteservidor.preciosclaros.model.Department;
import com.clienteservidor.preciosclaros.model.Locality;

@Repository("localityDao")
public class LocalityDaoImpl extends GenericDaoImpl<Locality> implements LocalityDao {
	public LocalityDaoImpl(){
		this.entityClass = Locality.class; 
	}

	@SuppressWarnings("unchecked")
	public Collection<Locality> findAll(Integer offset, Integer limit, String query, Department department) {

		return (Collection<Locality>) getQuery().setOffset(offset).setLimit(limit)
				.filterEq("department", department).search(query, "name")
				.getResults();
	}

	public int length(String query, Department department) {
		return getQuery().filterEq("department", department).search(query, "name")
				.getResults().size();
	}
}
