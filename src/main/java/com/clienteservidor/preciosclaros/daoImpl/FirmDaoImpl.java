package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.dao.FirmDao;
import com.clienteservidor.preciosclaros.model.Company;
import com.clienteservidor.preciosclaros.model.Firm;
import com.clienteservidor.preciosclaros.model.Product;

@Repository("firmDao")
public class FirmDaoImpl extends GenericDaoImpl<Firm> implements FirmDao{

	public FirmDaoImpl() {
		this.entityClass = Firm.class; 
	}


	@SuppressWarnings("unchecked")
	public Collection<Firm> findAll(Integer offset, Integer limit, Company company, String query) {

		return (Collection<Firm>) getQuery().setOffset(offset).setLimit(limit)
				.filterEq("company", company).search(query, "name")
				.getResults();
	}


	public int length(Company company, String query) {

		return getQuery().filterEq("company", company).search(query, "name")
				.getResults().size();
	}
}
