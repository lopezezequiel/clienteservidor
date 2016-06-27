package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.dao.BranchDao;
import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.Locality;
import com.clienteservidor.preciosclaros.model.Firm;

@Repository("branchDao")
public class BranchDaoImp extends GenericDaoImpl<Branch> implements BranchDao{

	public BranchDaoImp() {
		this.entityClass=Branch.class;
	}

	@SuppressWarnings("unchecked")
	public Collection<Branch> findAll(Integer offset, Integer limit, Firm firm,
			Locality locality, Double latitude, Double longitude) {
		
		
		if(latitude != null & longitude != null) {
			
			String hql = "SELECT b AS branch, sqrt("
					+ "power(111.302616974 * (b.location.latitude - :lat), 2) + "
					+ "power(111.302616974 * (:long - b.location.longitude) * cos(b.location.latitude / 57.3), 2)"
					+ ") AS distance FROM Branch AS b ORDER BY distance";

			Query query = getSession().createQuery(hql);
			
			query.setDouble("lat", latitude);
			query.setDouble("long", longitude);

	    	if(offset != null) {
	    		query.setFirstResult(offset);
	    	}

	    	if(limit != null) {
	    		query.setMaxResults(limit);
	    	}

	    	Collection<Branch> branches = new LinkedList<Branch>();
	    	query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	    	Map<String,Object> row;
	    	Branch branch;

	    	for (Object object : query.list()) {
	    	      row  = (Map<String,Object>) object;
	    	      branch = (Branch) row.get("branch");
	    	      branch.setDistance((Double) row.get("distance"));
	    	      branches.add(branch);
	    	}

			return branches;
		}
		
		return (Collection<Branch>) getQuery().setOffset(offset).setLimit(limit)
				.filterEq("firm", firm).filterEq("locality", locality)
				.getResults();
	}

	public int length(Firm firm, Locality locality) {
		return getQuery().filterEq("firm", firm).filterEq("locality", locality)
				.getResults().size();
	}

}
