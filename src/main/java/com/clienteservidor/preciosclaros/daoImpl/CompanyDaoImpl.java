package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.dao.CompanyDao;
import com.clienteservidor.preciosclaros.model.Company;

@Repository("companyDao")
public class CompanyDaoImpl extends GenericDaoImpl<Company> implements CompanyDao {
	
	public CompanyDaoImpl(){
		this.entityClass = Company.class; 
	}

	@SuppressWarnings("unchecked")
	public Collection<Company> findAll(Integer offset, Integer limit, String query) {
		return (Collection<Company>) getQuery().setOffset(offset).setLimit(limit)
				.search(query, "name")
				.getResults();
	}

	public int length(String query) {
		return getQuery().search(query, "name").getResults().size();
	}

}
