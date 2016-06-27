package com.clienteservidor.preciosclaros.daoImpl;


import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.dao.DepartmentDao;
import com.clienteservidor.preciosclaros.model.Department;
import com.clienteservidor.preciosclaros.model.Province;

@Repository("departmentDao")
public class DepartmentDaoImpl extends GenericDaoImpl<Department> implements DepartmentDao {
	
	public DepartmentDaoImpl() {
		this.entityClass = Department.class; 
	}

	@SuppressWarnings("unchecked")
	public Collection<Department> findAll(Integer offset, Integer limit, Province province, String query) {
		return (Collection<Department>) getQuery().setOffset(offset).setLimit(limit)
				.filterEq("province", province).search(query, "name")
				.getResults();
	}

	public int length(String query, Province province) {
		return getQuery().filterEq("province", province)
				.search(query, "name").getResults().size();
	}

}