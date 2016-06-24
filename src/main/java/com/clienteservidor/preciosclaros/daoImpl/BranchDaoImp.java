package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.dao.BranchDao;
import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.Locality;
import com.clienteservidor.preciosclaros.model.Product;
import com.clienteservidor.preciosclaros.model.Firm;

@Repository("branchDao")
public class BranchDaoImp extends GenericDaoImpl<Branch> implements BranchDao{
	
	public BranchDaoImp() {
		this.entityClass=Branch.class;
	}

	@SuppressWarnings("unchecked")
	public Collection<Branch> findAll(Integer offset, Integer limit, Firm firm, Locality locality) {
		return (Collection<Branch>) getQuery().setOffset(offset).setLimit(limit)
				.filterEq("firm", firm).filterEq("locality", locality)
				.getResults();
	}

	public int length(Firm firm, Locality locality) {
		return getQuery().filterEq("firm", firm).filterEq("locality", locality)
				.getResults().size();
	}

}
