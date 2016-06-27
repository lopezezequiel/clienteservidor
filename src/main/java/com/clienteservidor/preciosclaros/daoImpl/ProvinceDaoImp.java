package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.dao.ProvinceDao;
import com.clienteservidor.preciosclaros.model.Province;

@Repository("provinceDao")
public class ProvinceDaoImp extends GenericDaoImpl<Province> implements ProvinceDao {
	
	public ProvinceDaoImp() {
		this.entityClass = Province.class; 
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Province> findAll(Integer offset, Integer limit, String query) {
		return (Collection<Province>) getQuery().setOffset(offset).setLimit(limit)
				.search(query, "name").getResults();
	}

	public int length(String query) {
		return getQuery().search(query, "name").getResults().size();
	}

}
